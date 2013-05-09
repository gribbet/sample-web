package server.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.slf4j.Logger;

import server.logger.InjectLogger;
import server.service.ExecutorService;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class ExecutorServiceImpl implements ExecutorService {
	@InjectLogger
	private Logger logger;
	@Inject
	private Injector injector;
	private ScheduledThreadPoolExecutor executor;
	private final List<Task> tasks = new ArrayList<Task>();
	private Integer count = 0;

	public ExecutorServiceImpl() {
		executor = new ScheduledThreadPoolExecutor(6);
	}

	@Override
	public <T> Future<T> execute(final Callable<T> callable, final String name) {
		final Task task = createTask(name);

		injector.injectMembers(callable);
		final java.util.concurrent.Future<T> future = executor.submit(new Callable<T>() {
			@Override
			public T call() throws Exception {
				startTask(task);
				T t = null;
				try {
					t = callable.call();
				} catch (Exception e) {
					if (!isShutdown())
						logger.error("Execute failure for task \"" + name + "\"", e);
				}
				endTask(task);
				return t;
			}
		});
		return new Future<T>() {
			@Override
			public boolean isDone() {
				return future.isDone();
			}

			@Override
			public T get() {
				try {
					return future.get();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} catch (ExecutionException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	@Override
	public void execute(final Runnable runnable, String name) {
		injector.injectMembers(runnable);
		execute(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				runnable.run();
				return null;
			}
		}, name);
	}

	private class Task {
		public Integer id;
		public String name;
		public boolean running = false;
		public Date startTime;
		public Date endTime;

		@Override
		public String toString() {
			return "Task: { id: " + id + ", name: \"" + name + "\"}";
		}
	}

	private synchronized Task createTask(String name) {
		Task task = new Task();
		task.name = name;
		task.id = ++count;
		tasks.add(task);
		logger.debug("Task created: \"" + task.name + "\" (" + task.id + ")");
		logTasks();
		return task;
	}

	private synchronized void startTask(Task task) {
		task.startTime = new Date();
		task.running = true;
		logger.debug("Task started: \"" + task.name + "\" (" + task.id + ")");
		logTasks();
	}

	private synchronized void endTask(Task task) {
		task.endTime = new Date();
		task.running = false;
		tasks.remove(task);
		logger.debug("Task ended: \"" + task.name + "\" (" + task.id + ") - " + (task.endTime.getTime() - task.startTime.getTime()) + "ms");
		logTasks();
	}

	private void logTasks() {
		if (logger.isDebugEnabled()) {
			Date now = new Date();
			String log = "";
			for (Task task : tasks) {
				if (!task.running)
					continue;
				if (log.length() > 0)
					log += ", ";
				log += "{ id: " + task.id + ", name: " + task.name + ", time: " + (now.getTime() - task.startTime.getTime()) + "ms }";
			}
			log = "[" + log + "]";
			logger.debug("Running tasks: " + log);
		}
	}

	public void shutdown() {
		List<Runnable> tasks = executor.shutdownNow();
		logger.info("Shutting down executor. " + tasks.size() + " tasks running.");
	}

	public boolean isShutdown() {
		return executor.isShutdown();
	}

}
