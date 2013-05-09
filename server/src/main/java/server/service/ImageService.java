package server.service;


import java.io.InputStream;

import server.domain.Image;
import server.service.impl.ImageServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(ImageServiceImpl.class)
public interface ImageService extends AbstractDomainService<Integer, Image> {
	public Image create(InputStream stream);

	public Image getDefaultImage();

	public InputStream getStream(Image image, Integer width, Integer height);
}
