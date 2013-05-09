package server.server;

import java.io.IOException;
import java.util.EnumSet;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.servlet.DispatcherType;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import server.configuration.Configuration;

import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;

public class Server {
	private HttpServer httpServer;

	public Server() throws IOException {
		WebappContext context = new WebappContext("Server", "");
		context.addFilter("guice", GuiceFilter.class).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),
				"/*");
		context.addListener(listener());

		httpServer = new HttpServer();

		NetworkListener listener = new NetworkListener("Server", Configuration.host, Configuration.port);
		if (Configuration.useSsl) {
			listener.setSecure(true);
			listener.setSSLEngineConfig(sslConfiguration());
		}
		httpServer.addListener(listener);
		httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler(), "/");

		context.deploy(httpServer);

		httpServer.start();
	}

	private SSLEngineConfigurator sslConfiguration() {
		SSLContextConfigurator sslContext = new SSLContextConfigurator();
		sslContext.setKeyStoreFile(Configuration.sslKeyStorePath);
		sslContext.setKeyPass(Configuration.sslKeyStorePassword);
		SSLEngineConfigurator sslConfigurator = new SSLEngineConfigurator(sslContext);
		sslConfigurator.setClientMode(false);
		sslConfigurator.setNeedClientAuth(false);
		return sslConfigurator;
	}

	public void stop() {
		httpServer.stop();
	}

	protected Class<? extends GuiceServletContextListener> listener() {
		return GuiceServletConfig.class;
	}
}
