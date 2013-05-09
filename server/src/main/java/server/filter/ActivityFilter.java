package server.filter;


import javax.persistence.PersistenceException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import server.domain.Authentication;
import server.logger.InjectLogger;
import server.service.ActivityService;
import server.service.ExecutorService;

import com.google.inject.Inject;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Provider
public class ActivityFilter implements ContainerRequestFilter {
	@InjectLogger
	private Logger logger;
	@Inject
	private ExecutorService executorService;
	@Context
	private HttpContext context;

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) {
		String method = containerRequest.getMethod();
		if (!method.equals("POST") && !method.equals("GET") && !method.equals("PUT") && !method.equals("DELETE"))
			return containerRequest;
		final Authentication authentication = (Authentication) context.getProperties().get("authentication");
		executorService.execute(new Runnable() {
			@Inject
			private ActivityService activityService;

			@Override
			public void run() {
				try {
					activityService.record("API");
					if (authentication != null)
						activityService.record("API: " + authentication.getName());
				} catch (PersistenceException e) {
					logger.warn("Activity could not be recorded");
				}
			}
		}, "Activity");

		return containerRequest;
	}
}
