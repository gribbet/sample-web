package server.test;


import org.junit.AfterClass;
import org.junit.BeforeClass;

import server.domain.Authentication;
import server.domain.Token;
import server.server.Server;
import server.test.server.TestServer;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;

public class AbstractClientTest extends AbstractTest {
	private static Server server;

	@BeforeClass
	public static void startServer() throws Exception {
		server = new TestServer();
	}

	@AfterClass
	public static void stopServer() throws Exception {
		server.stop();
	}

	protected WebResource resource(String uri) {
		return resource(uri, null, null);
	}

	protected WebResource resource(String uri, Authentication authentication) {
		return resource(uri, authentication, null);
	}

	protected WebResource resource(String uri, Authentication authentication, Token token) {
		OAuthParameters parameters = new OAuthParameters().signatureMethod("HMAC-SHA1");
		OAuthSecrets secrets = new OAuthSecrets();
		if (authentication != null) {
			parameters.setConsumerKey(authentication.getKey());
			secrets.setConsumerSecret(authentication.getSecret());
		}
		if (token != null) {
			parameters.token(token.getKey());
			secrets.tokenSecret(token.getSecret());
		}
		Client client = Client.create();
		OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), parameters, secrets);
		WebResource resource = client.resource(uri);
		resource.addFilter(filter);
		return resource;
	}
}
