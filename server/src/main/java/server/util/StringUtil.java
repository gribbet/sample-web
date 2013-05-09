package server.util;

public class StringUtil {
	public static String truncate(String s, int length) {
		if (s.length() > length)
			return s.substring(0, length);
		return s;
	}
}
