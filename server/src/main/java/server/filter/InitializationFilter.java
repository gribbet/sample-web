package server.filter;

import javax.ws.rs.ext.Provider;

import server.domain.Authentication;
import server.domain.Message;
import server.domain.User;
import server.service.AuthenticationService;
import server.service.MessageService;
import server.service.UserService;

import com.google.inject.Inject;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Provider
public class InitializationFilter implements ContainerRequestFilter {
	private boolean initialized = false;
	@Inject
	private AuthenticationService authenticationService;
	@Inject
	private UserService userService;
	@Inject
	private MessageService messageService;

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		synchronized (this) {
			if (!initialized) {
				Authentication authentication = new Authentication();
				authentication.setName("Admin");
				authentication.setAdmin(true);
				authentication.setKey("d48e11e48b6d5a60dcbb3be8a2774dcd0b9cb5fc");
				authentication.setSecret("af5af75814a37b499dc8655d4d440d781164c9a5");
				authenticationService.create(authentication);

				User user = new User();
				user.setUsername("test");
				user.setEmail("test@test.com");
				user.setPassword("test12345");
				user.setName("Test User");
				userService.create(user);

				for (int i = 1; i <= 25; i++) {
					Message message = new Message();
					message.setUser(user);
					message.setSubject("Test message " + i);
					message.setMessage("");
					messageService.create(message);
				}

				initialized = true;
			}
		}
		return request;
	}
}
