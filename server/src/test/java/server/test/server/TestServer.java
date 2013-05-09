package server.test.server;


import java.io.IOException;

import server.server.Server;

import com.google.inject.servlet.GuiceServletContextListener;

public class TestServer extends Server {
	public TestServer() throws IOException {
		super();
	}

	@Override
	protected Class<? extends GuiceServletContextListener> listener() {
		return TestGuiceServletConfig.class;
	}
}
