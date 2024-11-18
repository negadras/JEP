package com.jep;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static com.jep.SimulatedIOTask.execute;

/**
 * Demonstrates the use of Virtual Threads as introduced in JEP 444.
 *
 * <p>JEP 444 finalizes virtual threads, making them a standard feature in Java 21.
 * Virtual threads are lightweight, allowing applications to scale efficiently with
 * a thread-per-task model while maintaining high throughput.</p>
 *
 * <p>This class provides an example of using virtual threads to execute a large number
 * of concurrent tasks. It highlights the simplicity and efficiency of virtual threads
 * by creating and running 10,000 tasks concurrently, with each task running on its
 * own virtual thread.</p>
 *
 * <h2>Features Demonstrated</h2>
 * <ul>
 *   <li>Using {@code Thread.ofVirtual()} to create a virtual thread factory</li>
 *   <li>Leveraging {@code Executors.newThreadPerTaskExecutor()} to manage virtual threads</li>
 *   <li>Executing a large number of lightweight tasks with minimal resource overhead</li>
 * </ul>
 *
 * <h2>Benefits of Virtual Threads</h2>
 * <ul>
 *   <li>Scalable concurrency: Virtual threads are cheap to create and support millions of concurrent tasks.</li>
 *   <li>Simple thread-per-task model: Makes concurrent programming easier by allowing each task to have its own thread.</li>
 *   <li>Efficient use of system resources: Virtual threads share platform threads and avoid blocking OS resources during I/O or other blocking operations.</li>
 * </ul>
 *
 * @since Java 21
 */

public class VirtualThread {

    public static void main( String[] args ) {
        System.out.println( "===== Example 1: Creating Virtual Threads ====" );

        ThreadFactory virtualThreadFactory = Thread.ofVirtual().factory();

        Instant start = Instant.now();

        // Create an executor that uses virtual threads
        try (var executorService = Executors.newThreadPerTaskExecutor(virtualThreadFactory)) {
            for (int i = 0; i < 10_000; i++) {
                int taskId = i;
                executorService.submit(() -> execute(taskId));
            }
        }  // The executor is automatically closed here, waiting for all tasks to complete

        Instant end = Instant.now();
        System.out.println("All tasks completed in "+ Duration.between(start, end).toMillis() + "ms");
    }
}
