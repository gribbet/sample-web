package server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	public static Date previousHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		return calendar.getTime();
	}

	public static Date previousDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		return calendar.getTime();
	}

	public static Date truncateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date today() {
		return truncateTime(new Date());
	}

	public static Date nextMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
		return calendar.getTime();
	}

	public static Date nextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 1);
		return calendar.getTime();
	}

	public static String formatDate(Date date) {
		if (date == null)
			return null;
		return dateFormat.format(date);
	}

	public static Date parseDate(String dateString) {
		if (dateString == null)
			return null;
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatDateTime(Date date) {
		if (date == null)
			return null;
		return dateTimeFormat.format(date);
	}

	public static Date parseDateTime(String dateString) {
		if (dateString == null)
			return null;
		try {
			return dateTimeFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}
}
