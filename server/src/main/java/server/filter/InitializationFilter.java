package server.filter;

import javax.ws.rs.ext.Provider;

import server.domain.Message;
import server.domain.User;
import server.service.MessageService;
import server.service.UserService;

import com.google.inject.Inject;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Provider
public class InitializationFilter implements ContainerRequestFilter {
	private boolean initialized = false;
	@Inject
	private UserService userService;
	@Inject
	private MessageService messageService;

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		synchronized (this) {
			if (!initialized) {
				User user = new User();
				user.setUsername("test");
				user.setEmail("test@test.com");
				user.setPassword("test12345");
				user.setName("Test User");
				userService.create(user);

				Message message = new Message();
				message.setUser(user);
				message.setSubject("Test message");
				message.setMessage("Content");
				messageService.create(message);

				initialized = true;
			}
		}
		return request;
	}
}
