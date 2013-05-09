package server.service;


import java.util.concurrent.Callable;

import server.service.impl.ExecutorServiceImpl;

import com.google.inject.ImplementedBy;

@ImplementedBy(ExecutorServiceImpl.class)
public interface ExecutorService {
	public <T> Future<T> execute(Callable<T> caller, String name);

	public void execute(final Runnable runnable, String name);

	public interface Future<T> {
		public boolean isDone();

		public T get();
	}
}
