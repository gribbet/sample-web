package server.logger;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import server.configuration.Configuration;
import server.filter.LoggingFilter;

public class ServerLogger {
	public static void configure() {
		Logger.getRootLogger().getLoggerRepository().resetConfiguration();

		final String pattern = "%-5p %d{ISO8601} %-26.26c{1} - %m%n";

		ConsoleAppender console = new ConsoleAppender();
		console.setLayout(new PatternLayout(pattern));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);

		if (!StringUtils.isEmpty(Configuration.logDebugPath)) {
			DailyRollingFileAppender appender = new DailyRollingFileAppender();
			appender.setFile(Configuration.logDebugPath);
			appender.setThreshold(Level.DEBUG);
			appender.setDatePattern("'.'yyyy-MM-dd");
			appender.setLayout(new PatternLayout(pattern));
			appender.activateOptions();
			Logger.getRootLogger().addAppender(appender);
		}

		if (!StringUtils.isEmpty(Configuration.logInfoPath)) {
			DailyRollingFileAppender appender = new DailyRollingFileAppender();
			appender.setFile(Configuration.logInfoPath);
			appender.setThreshold(Level.INFO);
			appender.setDatePattern("'.'yyyy-MM-dd");
			appender.setLayout(new PatternLayout(pattern));
			appender.activateOptions();
			Logger.getRootLogger().addAppender(appender);
		}

		if (!StringUtils.isEmpty(Configuration.logAccessPath)) {
			DailyRollingFileAppender appender = new DailyRollingFileAppender();
			appender.setFile(Configuration.logAccessPath);
			appender.setThreshold(Level.DEBUG);
			appender.setDatePattern("'.'yyyy-MM-dd");
			appender.setLayout(new PatternLayout("%d{ISO8601} - %m%n"));
			appender.activateOptions();
			Logger.getLogger(LoggingFilter.class).addAppender(appender);
		}

		if (!StringUtils.isEmpty(Configuration.logEmailRecipients) && !StringUtils.isEmpty(Configuration.smtpHost)) {
			LimitingSMTPAppender appender = new LimitingSMTPAppender();
			appender.setTo(Configuration.logEmailRecipients);
			appender.setFrom("server@localhost");
			appender.setSubject("Server Error!");
			appender.setThreshold(Level.ERROR);
			appender.setBufferSize(64);
			appender.setSMTPHost(Configuration.smtpHost);
			appender.setSMTPUsername(Configuration.smtpUsername);
			appender.setSMTPPassword(Configuration.smtpPassword);
			appender.setLayout(new PatternLayout(pattern));
			appender.activateOptions();
			Logger.getRootLogger().addAppender(appender);
		}
	}
}
