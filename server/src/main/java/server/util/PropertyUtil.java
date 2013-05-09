package server.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class PropertyUtil {
	public static <T> void copyModifiableProperties(T to, T from) {
		List<String> modifiableProperties = getModifiableProperties(to.getClass());
		for (String property : modifiableProperties) {
			try {
				Object value = PropertyUtils.getProperty(from, property);
				if (value != null)
					PropertyUtils.setProperty(to, property, value);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static Map<Class<?>, List<String>> cachedProperties = new HashMap<Class<?>, List<String>>();

	private static synchronized List<String> getModifiableProperties(Class<?> type) {
		if (cachedProperties.containsKey(type))
			return cachedProperties.get(type);
		List<String> properties = new ArrayList<String>();

		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(type).getPropertyDescriptors())
				if (pd.getReadMethod().isAnnotationPresent(Modifiable.class))
					properties.add(pd.getName());
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}

		Field[] fields = type.getDeclaredFields();
		for (Field field : fields)
			if (field.isAnnotationPresent(Modifiable.class))
				properties.add(field.getName());

		cachedProperties.put(type, properties);

		return properties;
	}
}
