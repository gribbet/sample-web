package server.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class ConfigurationReader {
	public static void read() {
		Configuration configuration = new Configuration();
		try {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream("configuration.properties"));
			} catch (FileNotFoundException e) {
				return;
			}
			for (Object key : properties.keySet()) {
				Field field = Configuration.class.getField(key.toString());
				if (field == null)
					continue;
				Object value = properties.get(key);
				if (!field.getType().equals(String.class)) {
					Method parseMethod = field.getType().getMethod("valueOf", new Class[] { String.class });
					value = parseMethod.invoke(field, value);
				}
				field.set(configuration, value);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
