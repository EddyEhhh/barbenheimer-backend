package com.barbenheimer.movieservice;

import com.barbenheimer.movieservice.dto.MovieShortDTO;
import com.barbenheimer.movieservice.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieIntegrationTest {

        @LocalServerPort
        private int port;

        private final String baseUrl = "http://localhost:";

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private MovieRepository movies;

    @Test
    public void getAllMovies() throws URISyntaxException  {
        URI uri = new URI(baseUrl + port + "/api/v1/movies");

        ResponseEntity<MovieShortDTO[]> result = restTemplate.getForEntity(uri, MovieShortDTO[].class);
        MovieShortDTO[] movies = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, movies[0].getId());
    }

    @Test
    public void getMoviesBySearch() throws URISyntaxException {
        URI uri = new URI(baseUrl + port + "/api/v1/movies");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri))
                .queryParam("q", "Mission");

        ResponseEntity<MovieShortDTO[]> result = restTemplate.getForEntity(builder.build().toUri(), MovieShortDTO[].class);
        MovieShortDTO[] movies = result.getBody();

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Mission: Impossible - Dead Reckoning Part One", movies[0].getTitle());
    }
}
