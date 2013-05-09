package server.service.impl;

import java.util.Random;

import server.domain.Authentication;
import server.service.AuthenticationService;
import server.util.HashUtil;

import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class AuthenticationServiceImpl extends AbstractDomainServiceImpl<String, Authentication> implements AuthenticationService {
	private Random random = new Random();

	public AuthenticationServiceImpl() {
		super(Authentication.class);
	}

	@Override
	@Transactional
	public void create(Authentication authentication) {
		if (authentication.getKey() == null)
			authentication.setKey(HashUtil.hash("" + random.nextDouble()));
		if (authentication.getSecret() == null)
			authentication.setSecret(HashUtil.hash("" + random.nextDouble()));
		super.create(authentication);
	}
}
