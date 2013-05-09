package server.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import server.configuration.Configuration;
import server.domain.File;
import server.service.FileService;
import server.util.StringUtil;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class FileServiceImpl extends AbstractDomainServiceImpl<Integer, File> implements FileService {

	public FileServiceImpl() {
		super(File.class);
		new java.io.File(Configuration.filePath).mkdirs();
	}

	@Override
	@Transactional
	public File create(String fileName, java.io.File file) {
		try {
			return create(fileName, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public File create(String fileName, InputStream stream) {
		File file = new File();
		file.setFileName(fileName);
		create(file);

		try {
			IOUtils.copy(stream, new FileOutputStream(getSystemPath(file)));
		} catch (Exception e) {
			delete(file);
			throw new RuntimeException(e);
		}

		file.setSize(new java.io.File(getSystemPath(file)).length());

		update(file);

		return file;
	}

	@Override
	public InputStream getStream(File file) {
		try {
			return new FileInputStream(getSystemPath(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public void delete(File file) {
		new java.io.File(getSystemPath(file)).delete();
		super.delete(file);
	}

	public String getSystemPath(File file) {
		String filename = StringUtil.truncate(file.getId() + "-" + file.getFileName().replaceAll("([a-z][A-Z]-_\\.)+", "_"), 64);
		return Configuration.filePath + "/" + filename;
	}
}
