package server.test;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import server.configuration.Configuration;
import server.domain.Authentication;
import server.domain.Message;
import server.domain.Token;
import server.domain.User;
import server.logger.InjectLogger;
import server.resource.MessageResource;
import server.resource.TokenResource;
import server.resource.UserResource;
import server.service.AuthenticationService;
import server.util.Error;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

public class BasicTest extends AbstractClientTest {
	@InjectLogger
	private Logger logger;
	@Inject
	private AuthenticationService authenticationService;
	@Inject
	private UnitOfWork unitOfWork;
	@Inject
	private PersistService persistService;
	private Authentication authentication;

	@Before
	public void initialize() {
		persistService.start();
		unitOfWork.begin();

		authentication = new Authentication();
		authentication.setName("Test Authentication");
		authenticationService.create(authentication);
		logger.info("Created test authentication: " + authentication);

		unitOfWork.end();
		persistService.stop();
	}

	@Test
	public void test() throws IOException, ParseException {
		User user1 = createTestUser("test1");
		User user2 = createTestUser("test2");

		Token token1 = getToken(user1);
		Token token2 = getToken(user2);

		createTestMessage(user1, "Test Message 1", "Test", token1);
		createTestMessage(user1, "Test Message 2", "Testing", token1);
		createTestMessage(user2, "Test Message 3", "Blah Blah", token2);

		uploadImage(user1, token1);
	}

	private User createTestUser(String username) {
		User user = new User();
		user.setName(username);
		user.setUsername(username);
		user.setPassword("password");
		user.setEmail(username + "@test.com");

		WebResource resource = resource(Configuration.baseUri + UserResource.PATH, authentication);
		ClientResponse response = resource.post(ClientResponse.class, user);
		checkError(response);
		String uri = response.getHeaders().getFirst("Content-Location");
		user = resource(uri, authentication).get(User.class);

		logger.info("Created test user: " + user);

		return user;
	}

	private Token getToken(User user) {
		WebResource resource = resource(Configuration.baseUri + TokenResource.PATH, authentication);
		resource = resource.queryParam("username", user.getUsername()).queryParam("password", "password");
		Token token = resource.post(Token.class);
		logger.info("Got token: " + token);
		return token;
	}

	private Message createTestMessage(User user, String subject, String text, Token token) {
		Message message = new Message();
		message.setUser(new User());
		message.getUser().setId(user.getId());
		message.setSubject(subject);
		message.setMessage(text);
		WebResource resource = resource(Configuration.baseUri + MessageResource.PATH, authentication);
		ClientResponse response = resource.post(ClientResponse.class, message);
		checkError(response);
		String uri = response.getHeaders().getFirst("Content-Location");
		message = resource(uri, authentication).get(Message.class);

		logger.info("Created test message: " + message);
		return message;
	}

	private void uploadImage(User user, Token token) {
		WebResource resource = resource(Configuration.baseUri + UserResource.PATH + "/" + user.getId() + "/image", authentication, token);
		FormDataMultiPart formData = new FormDataMultiPart();
		formData.bodyPart(new StreamDataBodyPart("image", getClass().getResourceAsStream("/cat.jpg")));
		ClientResponse response = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, formData);
		checkError(response);
		logger.info("User image posted: " + response.getEntity(String.class));
	}

	private void checkError(ClientResponse response) {
		if (response.getStatus() < 300)
			return;
		if (response.getStatus() == 400) {
			Error error = response.getEntity(Error.class);
			throw new RuntimeException("Error: " + error.getMessage());
		} else
			throw new RuntimeException("Error: " + response.getStatus() + " " + response.getEntity(String.class));
	}
}
