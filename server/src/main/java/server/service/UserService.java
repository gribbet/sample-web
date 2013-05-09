package server.service;


import java.util.List;

import server.domain.User;
import server.service.impl.UserServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(UserServiceImpl.class)
public interface UserService extends AbstractDomainService<Integer, User> {
	public enum SortType {
		DEFAULT;

		public static SortType fromString(String value) {
			if (value == null)
				return null;
			return valueOf(value.toUpperCase());
		}
	}

	public List<User> list(String query, SortType sort, boolean reverse, Integer start, Integer count);

	public int count(String query);

	public User find(String username, String password);

	public User findByUsername(String username);

	public User findByEmail(String username);
}
