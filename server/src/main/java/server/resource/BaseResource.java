package server.resource;


import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import server.configuration.Configuration;
import server.domain.Authentication;
import server.domain.Token;
import server.domain.User;
import server.exception.UnauthorizedException;
import server.server.util.RequestAttribute;

@Produces({ MediaType.APPLICATION_JSON + ";qs=2", MediaType.APPLICATION_XML + ";qs=1" })
public class BaseResource {
	@RequestAttribute("authentication")
	private Authentication authentication;
	@RequestAttribute("token")
	private Token token;

	private boolean isAdmin() {
		if (!Configuration.secure)
			return true;
		if (authentication != null && authentication.isAdmin())
			return true;
		return false;
	}

	public void requireAdmin() {
		if (!isAdmin())
			throw new UnauthorizedException("insufficient_privileges", "Insufficient Privileges");
	}

	public void requireUser() {
		if (isAdmin())
			return;
		if (token == null)
			throw new UnauthorizedException("token_required", "OAuth token required");
	}

	public void requireUser(User user) {
		if (isAdmin())
			return;
		if (user == null || token == null || !token.getUser().getId().equals(user.getId()))
			throw new UnauthorizedException("insufficient_privileges", "Insufficient Privileges");
	}
}
