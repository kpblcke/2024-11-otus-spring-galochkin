package ru.otus.hw.controller;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GenresControllerRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @Test
    void shouldCheckGenresFlux() {
        var client = WebClient.create(String.format("http://localhost:%d", port));

        var expectedSize = 3;

        List<String> result = client
                .get().uri("/genre/all")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .take(expectedSize)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();

        assertThat(result).isNotNull().isNotEmpty().hasSize(expectedSize);
    }
}
