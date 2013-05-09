package server.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import server.configuration.Configuration;
import server.configuration.DomainModule;
import server.filter.AccessControlResponseFilter;
import server.filter.ActivityFilter;
import server.filter.DomainObjectResponseFilter;
import server.filter.InitializationFilter;
import server.filter.LoggingFilter;
import server.filter.OAuthAuthenticationFilter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;

public class GuiceServletConfig extends GuiceServletContextListener {
	final List<String> resourcePackages = Arrays.asList(//
			"server.resource", //
			"server.filter", //
			"server.server.exception", //
			"server.server.util");
	final List<Class<? extends ContainerRequestFilter>> requestFilters = new ArrayList<Class<? extends ContainerRequestFilter>>(
			Arrays.asList(//
					InitializationFilter.class, //
					OAuthAuthenticationFilter.class, //
					ActivityFilter.class, //
					LoggingFilter.class));
	final List<Class<? extends ContainerResponseFilter>> responseFilters = new ArrayList<Class<? extends ContainerResponseFilter>>(
			Arrays.asList(//
					LoggingFilter.class, //
					DomainObjectResponseFilter.class, //
					AccessControlResponseFilter.class));

	public GuiceServletConfig() {
		if (Configuration.logRequests) {
			requestFilters.add(0, com.sun.jersey.api.container.filter.LoggingFilter.class);
			responseFilters.add(com.sun.jersey.api.container.filter.LoggingFilter.class);
		}
	}

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(domainModule(), new ServletModule() {
			@Override
			protected void configureServlets() {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put("com.sun.jersey.config.property.packages", StringUtils.join(resourcePackages, ", "));
				properties.put("com.sun.jersey.spi.container.ContainerRequestFilters",
						StringUtils.join(requestFilters, ", ").replaceAll("class ", ""));
				properties.put("com.sun.jersey.spi.container.ContainerResponseFilters",
						StringUtils.join(responseFilters, ", ").replaceAll("class ", ""));
				properties.put("com.sun.jersey.config.feature.DisableWADL", "true");
				properties.put("com.sun.jersey.config.feature.logging.DisableEntitylogging", "true");

				serve("/*").with(GuiceContainer.class, properties);
				filter("/*").through(PersistFilter.class);
			}
		});
	}

	protected Module domainModule() {
		return new DomainModule();
	}
}