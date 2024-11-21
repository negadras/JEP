package com.jep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import static com.jep.Constants.NUM_REQUESTS;
import static com.jep.PokemonService.fetchPokemon;

public class PlatformThreadPokemonFetcher {

    private static final Logger LOGGER = Logger.getLogger(PlatformThreadPokemonFetcher.class.getName());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
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

        executorService.shutdown();
        long end = System.currentTimeMillis();
        long durationTime = end - start;

        LOGGER.info(String.format("Time taken: %dms", durationTime));
    }
}
