package ua.in.asilichenko.enigma.hillclimbing.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This service executes submitted tasks in parallel.
 * <p>
 * Copyright (C) 2022 Oleksii Sylichenko (a.silichenko@gmail.com)
 * <p>
 * License: LGPL-3.0-or-later
 * <p>
 * Creation date: 04.11.2022
 */
public class ThreadService {

    private final ExecutorService executorService;
    private final CountDownLatch countDownLatch;

    /**
     * Create service for execution of "expectedTaskCount" tasks in parallel.
     *
     * @param expectedTaskCount number of tasks to execute
     */
    @SuppressWarnings("WeakerAccess")
    public ThreadService(int expectedTaskCount) {
        executorService = Executors.newCachedThreadPool();
        countDownLatch = new CountDownLatch(expectedTaskCount);
    }

    /**
     * Add task for execution.
     *
     * @param task task
     */
    public void submit(Runnable task) {
        executorService.submit(task);
    }

    /**
     * Must be called at the end of each task.
     */
    public void countDown() {
        countDownLatch.countDown();
    }

    /**
     * Wait for all tasks to finish.
     */
    public void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted: ", e);
        }
        executorService.shutdown();
    }
}
