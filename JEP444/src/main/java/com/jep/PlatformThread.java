package com.jep;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.jep.SimulatedIOTask.execute;

/**
 * Demonstrates the use of traditional Platform Threads for comparison with Virtual Threads.
 *
 * <p>This class provides an example of using platform threads to execute a large number
 * of concurrent tasks. It serves as a counterpoint to the VirtualThread example,
 * highlighting the differences in implementation and performance characteristics
 * between platform threads and virtual threads.</p>
 *
 * <p>The example creates a fixed thread pool and submits 1,000 tasks for execution.
 * This approach is typical for managing concurrency with platform threads, where
 * the number of threads is limited to avoid overwhelming system resources.</p>
 *
 * <h2>Features Demonstrated</h2>
 * <ul>
 *   <li>Using {@code Executors.newFixedThreadPool()} to create a thread pool</li>
 *   <li>Submitting multiple tasks to a thread pool</li>
 *   <li>Proper shutdown and termination of an ExecutorService</li>
 *   <li>Calculating and displaying execution time for comparison purposes</li>
 * </ul>
 *
 * <h2>Characteristics of Platform Threads</h2>
 * <ul>
 *   <li>Limited Scalability: The number of concurrent threads is bounded by system resources</li>
 *   <li>Resource Intensive: Each platform thread consumes a significant amount of memory</li>
 *   <li>Thread Pooling: Typically used to manage and reuse a fixed number of threads</li>
 *   <li>One-to-One Mapping: Each platform thread corresponds to an OS thread</li>
 * </ul>
 *
 * <p>This example is designed to be run alongside the VirtualThread example for
 * performance and implementation comparison.</p>
 */
public class PlatformThread {

    public static void main(String[] args) {
        System.out.println("===== Example 2: Creating Platform Threads ====");
        Instant start = Instant.now();

        int nThreads = Runtime.getRuntime().availableProcessors() * 2;
        System.out.println("Number of threads: " + nThreads);

        try(ExecutorService executorService = Executors.newFixedThreadPool(nThreads)) {
            for (int i = 0; i < 10_000; i++) {
                int taskId = i;
                executorService.submit(() -> SimulatedIOTask.execute(taskId));
            }

            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.HOURS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Tasks interrupted");
            Thread.currentThread().interrupt();
        }

        Instant end = Instant.now();
        System.out.println("All tasks completed in " + Duration.between(start, end).toMillis() + "ms");
    }
}
