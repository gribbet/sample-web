package server.resource;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;

import server.domain.Image;
import server.logger.InjectLogger;
import server.service.ImageService;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;

@Path(ImageResource.PATH)
public class ImageResource extends BaseResource {
	public static final String PATH = "images";
	@InjectLogger
	private Logger logger;
	@Inject
	private ImageService imageService;

	@GET
	@Path("/{id}")
	public Image find(@PathParam("id") Integer id) {
		Image image = imageService.find(id);
		if (image == null)
			throw new NotFoundException();
		logger.info("Find Image " + id);
		return image;
	}
}
