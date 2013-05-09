package server.service;

import java.util.List;

import server.domain.Message;
import server.domain.User;
import server.service.impl.MessageServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(MessageServiceImpl.class)
public interface MessageService extends AbstractDomainService<Integer, Message> {
	public enum SortType {
		DEFAULT, TIME;

		public static SortType fromString(String value) {
			if (value == null)
				return null;
			return valueOf(value.toUpperCase());
		}
	}

	public List<Message> list(User user, SortType sort, boolean reverse, Integer start, Integer count);

	public List<Message> list(String query, SortType sort, boolean reverse, Integer start, Integer count);

	public int count(User user);

	public int count(String query);
}
