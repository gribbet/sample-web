package server.server.util;


import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import server.domain.Activity;
import server.domain.Authentication;
import server.domain.File;
import server.domain.Image;
import server.domain.Message;
import server.domain.Token;
import server.domain.User;
import server.util.Count;
import server.util.Error;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {
	private JAXBContext context;

	public JAXBContextResolver() throws Exception {
		context = new JSONJAXBContext(JSONConfiguration.natural().rootUnwrapping(true).build(), Authentication.class, File.class,
				Image.class, Message.class, Token.class, User.class, Error.class, Count.class, Activity.class);
	}

	public JAXBContext getContext(Class<?> objectType) {
		return context;
	}
}
