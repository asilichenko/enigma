/*
 *   Copyright (c) 2022 Oleksii Sylichenko.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ua.in.asilichenko.enigma.hillclimbing.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This service executes submitted tasks in parallel.
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
