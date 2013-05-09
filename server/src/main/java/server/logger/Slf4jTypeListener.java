package server.logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

public class Slf4jTypeListener implements TypeListener {
	public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
		for (Field field : getFields(typeLiteral.getRawType())) {
			if (field.getType() == Logger.class && field.isAnnotationPresent(InjectLogger.class)) {
				typeEncounter.register(new Slf4jMembersInjector<I>(field));
			}
		}
	}

	private List<Field> getFields(Class<?> type) {
		if (type == null)
			return Collections.emptyList();

		List<Field> fields = new ArrayList<Field>(Arrays.asList(type.getDeclaredFields()));
		fields.addAll(getFields(type.getSuperclass()));
		return fields;
	}
}