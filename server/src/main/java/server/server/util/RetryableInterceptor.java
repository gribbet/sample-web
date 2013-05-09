package server.server.util;


import javax.persistence.OptimisticLockException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;

import server.logger.InjectLogger;

public class RetryableInterceptor implements MethodInterceptor {
	@InjectLogger
	private Logger logger;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		int tries = 0;

		while (tries < 5) {
			tries++;

			try {
				return invocation.proceed();
			} catch (OptimisticLockException e) {
				logger.warn("Lock exception, retrying: " + invocation.getThis().getClass().getSimpleName() + "."
						+ invocation.getMethod().getName());
			}
		}

		return invocation.proceed();
	}

}
