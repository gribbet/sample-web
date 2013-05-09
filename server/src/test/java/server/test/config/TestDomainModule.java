package server.test.config;


import java.util.Properties;

import server.configuration.DomainModule;

public class TestDomainModule extends DomainModule {
	@Override
	protected Properties jpaProperties() {
		Properties properties = super.jpaProperties();
		properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:mem:unit-testing-jpa;sql.syntax_mys=true");
		properties.put("javax.persistence.jdbc.user", "");
		properties.put("javax.persistence.jdbc.password", "");
		properties.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
		properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		return properties;
	}
}
