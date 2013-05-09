package server.service;


import java.io.InputStream;

import server.domain.File;
import server.service.impl.FileServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(FileServiceImpl.class)
public interface FileService extends AbstractDomainService<Integer, File> {
	public File create(String fileName, java.io.File file);

	public File create(String fileName, InputStream stream);

	public InputStream getStream(File file);

	public String getSystemPath(File file);
}
