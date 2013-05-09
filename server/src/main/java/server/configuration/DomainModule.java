package server.configuration;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import java.util.Properties;

import server.logger.Slf4jTypeListener;
import server.server.util.Retryable;
import server.server.util.RetryableInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.jpa.JpaPersistModule;

public class DomainModule extends AbstractModule {
	@Override
	public void configure() {
		RetryableInterceptor retryableInterceptor = new RetryableInterceptor();
		requestInjection(retryableInterceptor);
		bindInterceptor(annotatedWith(Retryable.class), any(), retryableInterceptor);
		bindInterceptor(any(), annotatedWith(Retryable.class), retryableInterceptor);

		bindListener(Matchers.any(), new Slf4jTypeListener());

		install(new JpaPersistModule("server").properties(jpaProperties()));
	}

	protected Properties jpaProperties() {
		Properties properties = new Properties();
		properties.put("javax.persistence.jdbc.url", Configuration.databaseUrl);
		properties.put("javax.persistence.jdbc.user", Configuration.databaseUser);
		properties.put("javax.persistence.jdbc.password", Configuration.databasePassword);
		properties.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
		properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");

		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.format_sql", "true");

		properties.put("hibernate.connection.useUnicode", "true");
		properties.put("hibernate.connection.characterEncoding", "UTF-8");
		properties.put("hibernate.connection.charSet", "UTF-8");

		properties.put("hibernate.search.default.directory_provider", "ram");
		properties.put("hibernate.search.lucene_version", "LUCENE_35");
		properties.put("hibernate.search.autoregister_listeners", "true");

		properties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");

		properties.put("hibernate.cache.use_second_level_cache", "false");
		properties.put("hibernate.cache.use_query_cache", "false");
		properties.put("hibernate.generate_statistics", "false");
		properties.put("hibernate.cache.use_structured_entries", "true");

		properties.put("hibernate.c3p0.min_size", "0");
		properties.put("hibernate.c3p0.max_size", "10");
		properties.put("hibernate.c3p0.timeout", "500");
		properties.put("hibernate.c3p0.idle_test_period", "100");
		properties.put("hibernate.connection.provider_class", "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");

		return properties;
	}
}
