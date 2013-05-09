package server.logger;


import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.net.SMTPAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

import server.util.DateUtil;

public class LimitingSMTPAppender extends SMTPAppender {
	private Date lastSent;

	public LimitingSMTPAppender() {
		setEvaluator(new TriggeringEventEvaluator() {
			@Override
			public boolean isTriggeringEvent(LoggingEvent event) {
				Date current = new Date();
				boolean underThreshold = lastSent == null || current.compareTo(DateUtil.nextMinute(lastSent)) > 0;
				if (!event.getLevel().isGreaterOrEqual(Level.ERROR))
					return false;
				if (!underThreshold)
					return false;
				lastSent = new Date();
				return true;
			}
		});
	}
}
