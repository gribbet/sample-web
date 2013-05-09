package server.test.server;

import server.server.GuiceServletConfig;
import server.test.config.TestDomainModule;

import com.google.inject.Module;

public class TestGuiceServletConfig extends GuiceServletConfig {
	@Override
	protected Module domainModule() {
		return new TestDomainModule();
	}
}
