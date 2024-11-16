package com.jep;

import java.time.Duration;

public class SimulatedIOTask {

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
        System.out.println("Task " + taskId + " started: " + Thread.currentThread());
        try {
            Thread.sleep(Duration.ofMillis(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Task " + taskId + " completed: " + Thread.currentThread());
    }
}
