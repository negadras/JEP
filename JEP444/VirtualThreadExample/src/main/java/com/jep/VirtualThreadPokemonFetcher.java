package com.jep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static com.jep.Constants.NUM_REQUESTS;
import static com.jep.PokemonService.fetchPokemon;

public class VirtualThreadPokemonFetcher {

    private static final Logger LOGGER = Logger.getLogger(VirtualThreadPokemonFetcher.class.getName());

    public static void main( String[] args ) {
        long startTime = System.currentTimeMillis();

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<?>> futures = new ArrayList<>();

            for (int i = 1; i <= NUM_REQUESTS; i++) {
                final int pokemonId = i;
                futures.add(executorService.submit(() -> fetchPokemon(pokemonId)));
            }

            for (Future<?> future: futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    LOGGER.severe(e.getMessage());
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long durationTime = endTime - startTime;
        LOGGER.info(String.format("Total execution time: %d milliseconds", durationTime));
    }
}
