package server.resource;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;

import server.domain.Activity;
import server.logger.InjectLogger;
import server.service.ActivityService;

import com.google.inject.Inject;

@Path(ActivityResource.PATH)
public class ActivityResource extends BaseResource {
	public static final String PATH = "activities";
	@InjectLogger
	private Logger logger;
	@Inject
	private ActivityService activityService;

	@GET
	public List<Activity> list(@QueryParam("key") String key, @QueryParam("hourly") boolean hourly) {
		requireAdmin();
		logger.info("List activity");
		return activityService.list(key, hourly);
	}
}
