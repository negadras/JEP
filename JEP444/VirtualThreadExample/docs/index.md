# Comparing Platform Threads and Virtual Threads in Java

## Overview

This project compares the performance of platform threads and virtual threads in Java for making concurrent API requests to a locally hosted PokeAPI instance.

## Setup

- **API**: Locally hosted PokeAPI (http://localhost/api/v2/pokemon/)
- **Number of Requests**: 1025
- **Java Version**: Java 21 (for virtual thread support)

## Local API Setup

This project uses a locally hosted instance of the PokeAPI. To set up your own local instance:

1. Clone the PokeAPI repository:
   ```
   git clone https://github.com/PokeAPI/pokeapi.git
   ```

2. Follow the installation instructions in the [PokeAPI README](https://github.com/PokeAPI/pokeapi?tab=readme-ov-file).

3. Once set up, the API should be accessible at `http://localhost/api/v2/pokemon/`.

For detailed setup instructions and troubleshooting, refer to the [official PokeAPI documentation](https://github.com/PokeAPI/pokeapi?tab=readme-ov-file).

## Results

| Thread Type | Execution Time |
|-------------|----------------|
| Platform Threads | 1403 ms |
| Virtual Threads | 1017 ms |

Virtual threads showed a performance improvement of approximately 27.5% compared to platform threads.

## Code Structure

### 1. PlatformThreadPokemonFetcher

- Uses `Executors.newFixedThreadPool(10)` for concurrent execution
- Submits 1025 tasks to fetch Pokemon data
- Waits for all tasks to complete before shutting down the executor

### 2. VirtualThreadPokemonFetcher

- Uses `Executors.newVirtualThreadPerTaskExecutor()` for concurrent execution
- Submits 1025 tasks to fetch Pokemon data
- Automatically manages virtual threads

### 3. PokemonService

- Contains the `fetchPokemon` method used by both fetchers
- Makes HTTP requests to the PokeAPI
- Logs the response for each Pokemon

### 4. Constants

- Defines `NUM_REQUESTS` as 1025

## Key Observations

1. **Performance Gain**: Virtual threads completed the task approximately 386 ms faster than platform threads.
2. **Concurrency Model**: Platform threads used a fixed thread pool of 10 threads, while virtual threads created a new virtual thread per task.
3. **Code Similarity**: The implementation for both thread types is very similar, showcasing the ease of adopting virtual threads.
4. **Resource Utilization**: Further investigation into CPU and memory usage could provide additional insights.

## Conclusions

While virtual threads showed a notable performance improvement, the difference might be more pronounced in scenarios with:
- Higher latency operations
- Greater number of concurrent tasks
- More complex I/O operations

This experiment demonstrates that virtual threads can offer performance benefits even in relatively simple, low-latency scenarios.

## Future Work

- Increase the number of requests to test scalability
- Introduce artificial latency to simulate real-world network conditions
- Measure and compare resource utilization (CPU, memory) between the two approaches
- Test with varying levels of computation mixed with I/O operations