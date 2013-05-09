package server.server.util;

import java.lang.reflect.Type;

import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

@Provider
public class RequestAttributeInjector implements InjectableProvider<RequestAttribute, Type> {
	@Context
	private HttpContext context;

	public ComponentScope getScope() {
		return ComponentScope.PerRequest;
	}

	public Injectable<?> getInjectable(ComponentContext componentContext, RequestAttribute requestAttribute, final Type type) {
		final String name = requestAttribute.value();

		return new Injectable<Object>() {
			public Object getValue() {
				return context.getProperties().get(name);
			}
		};
	}
}
