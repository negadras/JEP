package com.jep;

import java.time.Duration;
import java.util.logging.Logger;

public class SimulatedIOTask {

    private static final Logger LOGGER = Logger.getLogger(SimulatedIOTask.class.getName());

    /**
     * Simulates an input/output bound task that takes some time to complete.
     *
     * <p>This method represents a typical I/O-bound task, such as a database query,
     * file read/write operation, or network request. It simulates the task by
     * introducing a delay and prints the start and completion of the task,
     * along with the thread information.</p>
     *
     * @param taskId The identifier for the task
     */
    static void execute(int taskId) {
        LOGGER.info("I/O task " + taskId + " started: " + Thread.currentThread());
        try {
            Thread.sleep(Duration.ofMillis(100));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warning("I/O task " + taskId + " interrupted: " + Thread.currentThread());
            return;
        }
        LOGGER.info("I/O task " + taskId + " completed: " + Thread.currentThread());
    }
}
