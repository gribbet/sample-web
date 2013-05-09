package server.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.core.InfoException;
import org.im4java.process.ProcessStarter;
import org.slf4j.Logger;

import server.configuration.Configuration;
import server.domain.File;
import server.domain.Image;
import server.domain.Image_;
import server.exception.MissingParameterException;
import server.exception.ServerException;
import server.logger.InjectLogger;
import server.service.FileService;
import server.service.ImageService;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class ImageServiceImpl extends AbstractDomainServiceImpl<Integer, Image> implements ImageService {
	@InjectLogger
	private Logger logger;
	@Inject
	private FileService fileService;

	public ImageServiceImpl() {
		super(Image.class);
		ProcessStarter.setGlobalSearchPath(Configuration.imageMagickPath);
		new java.io.File(Configuration.resizedImagePath).mkdirs();
	}

	@Override
	@Transactional
	public Image create(InputStream stream) {
		Image image = new Image();
		image.setInitialized(false);
		super.create(image);
		File file = fileService.create("image-" + image.getId(), stream);
		image.setSource(file);
		image.setInitialized(true);

		try {
			new Info(fileService.getSystemPath(file));
		} catch (InfoException e) {
			fileService.delete(file);
			delete(image);
			throw new ServerException("invalid_image", "Could not read image");
		}

		image = update(image);

		logger.info("Created Image");

		return image;
	}

	@Override
	public InputStream getStream(Image image, Integer width, Integer height) {
		if (width == null)
			width = 100;
		if (height == null)
			height = 100;
		if (width > 1000)
			width = 1000;
		if (height > 1000)
			height = 1000;

		String path = getPath(image, width, height);
		java.io.File resizedFile = new java.io.File(path);
		if (resizedFile.exists())
			try {
				return new FileInputStream(resizedFile);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}

		ConvertCmd command = new ConvertCmd();
		IMOperation operation = new IMOperation();
		operation.format("PNG");
		operation.addImage(fileService.getSystemPath(image.getSource()));
		operation.resize(width, height, "^");
		operation.extent(width, height);
		operation.gravity("center");
		operation.addImage(path);
		try {
			command.run(operation);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IM4JavaException e) {
			throw new RuntimeException(e);
		}

		return getStream(image, width, height);
	}

	private String getPath(Image image, Integer width, Integer height) {
		return Configuration.resizedImagePath + "/" + image.getId() + "-" + width + "x" + height + ".png";
	}

	@Override
	@Transactional
	public synchronized Image getDefaultImage() {
		Image image = findDefaultImage();
		if (image != null)
			return image;
		image = create(getClass().getResourceAsStream("/default.png"));
		image.setDefault(true);
		create(image);
		return image;
	}

	private Image findDefaultImage() {
		CriteriaBuilder builder = entityManager.get().getCriteriaBuilder();
		CriteriaQuery<Image> query = builder.createQuery(Image.class);
		Root<Image> root = query.from(Image.class);
		query.select(root);
		query.where(builder.and(builder.equal(root.get(Image_.isDefault), true)));
		try {
			TypedQuery<Image> typedQuery = entityManager.get().createQuery(query);
			typedQuery.setHint("org.hibernate.cacheable", true);
			return typedQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void validate(Image image) {
		if (image.isInitialized())
			if (image.getSource() == null)
				throw new MissingParameterException("source");
	}
}
