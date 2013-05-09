package server.filter;


import javax.ws.rs.ext.Provider;

import server.domain.DomainObject;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class DomainObjectResponseFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		if (response.getEntity() instanceof DomainObject) {
			DomainObject<?> domainObject = (DomainObject<?>) response.getEntity();
			response.getHttpHeaders().putSingle("Content-Location", domainObject.getUri());
			response.getHttpHeaders().putSingle("Content-ID", domainObject.getId());
		}
		return response;
	}

}
