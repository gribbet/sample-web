package server.resource;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;

import server.domain.File;
import server.logger.InjectLogger;
import server.service.FileService;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

@Path(FileResource.PATH)
public class FileResource extends BaseResource {
	public static final String PATH = "files";
	@InjectLogger
	private Logger logger;
	@Inject
	private FileService fileService;

	@GET
	@Path("/{id}")
	public File find(@PathParam("id") Integer id) {
		File file = fileService.find(id);
		if (file == null)
			throw new NotFoundException();
		logger.info("Find File " + id);
		return file;
	}
}
