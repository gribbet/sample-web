package server.service;

import server.domain.Authentication;
import server.service.impl.AuthenticationServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(AuthenticationServiceImpl.class)
public interface AuthenticationService extends AbstractDomainService<String, Authentication> {
}
