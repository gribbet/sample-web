package server.service.impl;


import java.util.Date;
import java.util.Random;

import server.domain.Token;
import server.domain.User;
import server.exception.MissingParameterException;
import server.service.TokenService;
import server.service.UserService;
import server.util.DateUtil;
import server.util.HashUtil;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class TokenServiceImpl extends AbstractDomainServiceImpl<String, Token> implements TokenService {
	@Inject
	private UserService userService;
	private Random random = new Random();

	public TokenServiceImpl() {
		super(Token.class);
	}

	@Override
	@Transactional
	public Token getToken(String username, String password) {
		if (username == null)
			throw new MissingParameterException("username");
		if (password == null)
			throw new MissingParameterException("password");
		User user = userService.find(username, password);
		if (user == null)
			return null;
		Token token = new Token();
		token.setExpiration(DateUtil.previousHour(new Date()));
		token.setUser(user);
		token.setSecret(HashUtil.hash("" + random.nextDouble()));
		create(token);
		return token;
	}
}
