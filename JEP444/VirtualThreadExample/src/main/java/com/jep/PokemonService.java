package com.jep;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class PokemonService {

    private static final Logger LOGGER = Logger.getLogger(PokemonService.class.getName());

    private static final String API_URL = "http://localhost/api/v2/pokemon/"; //"https://pokeapi.co/api/v2/pokemon/";

    public static void fetchPokemon(int pokemonId) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create(API_URL + pokemonId))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info(STR."Fetched Pokemon \{pokemonId}: \{response.body()}");
        } catch (Exception e) {
            LOGGER.severe(STR."Error fetching Pokemon: \{pokemonId}");
            e.printStackTrace();
        }
    }
}
