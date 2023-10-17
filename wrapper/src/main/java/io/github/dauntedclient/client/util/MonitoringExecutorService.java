/*
 * Daunted Client - the client for Daunt
 * Copyright (C) 2023  fwanchan and drifter16
 */

package io.github.dauntedclient.client.util;

import java.util.*;
import java.util.concurrent.*;

public class MonitoringExecutorService extends ThreadPoolExecutor {

	public MonitoringExecutorService(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	private final List<Future<?>> futures = new LinkedList<>();

	public List<Future<?>> getFutures() {
		return futures;
	}

	public void cancel() {
		futures.forEach((future) -> future.cancel(true));
	}

	@Override
	public Future<?> submit(Runnable task) {
		Future<?> future = super.submit(task);
		futures.add(future);
		return future;
	}

}
