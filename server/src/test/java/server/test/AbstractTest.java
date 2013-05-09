package server.test;


import org.junit.Before;
import org.junit.BeforeClass;

import server.logger.ServerLogger;
import server.test.config.TestDomainModule;

import com.google.inject.Guice;

public abstract class AbstractTest {
	@BeforeClass
	public static void configure() {
		ServerLogger.configure();
	}

	@Before
	public void inject() throws Exception {
		Guice.createInjector(new TestDomainModule()).injectMembers(this);
	}
}
