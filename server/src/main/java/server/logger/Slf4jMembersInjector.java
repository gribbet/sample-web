package server.logger;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.MembersInjector;

public class Slf4jMembersInjector<T> implements MembersInjector<T> {
	private final Field field;
	private final Logger logger;

	public Slf4jMembersInjector(Field field) {
		this.field = field;
		logger = LoggerFactory.getLogger(field.getDeclaringClass());
		field.setAccessible(true);
	}

	public void injectMembers(T arg) {
		try {
			field.set(arg, logger);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
