package server.service;

import server.domain.Token;
import server.service.impl.TokenServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(TokenServiceImpl.class)
public interface TokenService extends AbstractDomainService<String, Token> {
	public Token getToken(String username, String password);
}
