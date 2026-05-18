package com.hizik.rest.controller;

import com.hizik.rest.dto.MotoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MotoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateReadUpdateAndDeleteMotoPoint() {
        String baseUrl = "http://localhost:" + port + "/moto";

        ResponseEntity<MotoDto> createdResponse = restTemplate.postForEntity(
                baseUrl,
                new MotoDto(null, 55.7522F, 37.6156F),
                MotoDto.class
        );

        assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        MotoDto createdMoto = Objects.requireNonNull(createdResponse.getBody());
        assertThat(createdMoto.id()).isNotNull();
        assertThat(createdMoto.lat()).isEqualTo(55.7522F);
        assertThat(createdMoto.lon()).isEqualTo(37.6156F);

        ResponseEntity<MotoDto> loadedResponse = restTemplate.getForEntity(
                baseUrl + "/" + createdMoto.id(),
                MotoDto.class
        );

        assertThat(loadedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loadedResponse.getBody()).isEqualTo(createdMoto);

        restTemplate.put(
                baseUrl + "/" + createdMoto.id() + "?lat={lat}&lon={lon}",
                null,
                59.9386F,
                30.3141F
        );

        ResponseEntity<MotoDto> updatedResponse = restTemplate.getForEntity(
                baseUrl + "/" + createdMoto.id(),
                MotoDto.class
        );

        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        MotoDto updatedMoto = Objects.requireNonNull(updatedResponse.getBody());
        assertThat(updatedMoto.lat()).isEqualTo(59.9386F);
        assertThat(updatedMoto.lon()).isEqualTo(30.3141F);

        restTemplate.delete(baseUrl + "/" + createdMoto.id());

        ResponseEntity<String> deletedResponse = restTemplate.getForEntity(
                baseUrl + "/" + createdMoto.id(),
                String.class
        );

        assertThat(deletedResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateMotoPointFromRequestParams() {
        String baseUrl = "http://localhost:" + port + "/moto";

        ResponseEntity<MotoDto> createdResponse = restTemplate.postForEntity(
                baseUrl + "?lat={lat}&lon={lon}",
                null,
                MotoDto.class,
                44.6167F,
                33.5254F
        );

        assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        MotoDto createdMoto = Objects.requireNonNull(createdResponse.getBody());
        assertThat(createdMoto.id()).isNotNull();
        assertThat(createdMoto.lat()).isEqualTo(44.6167F);
        assertThat(createdMoto.lon()).isEqualTo(33.5254F);
    }

    @Test
    void shouldCreateMotoPointFromJsonBodyEvenWithTextPlainContentType() {
        String baseUrl = "http://localhost:" + port + "/moto";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        ResponseEntity<MotoDto> createdResponse = restTemplate.postForEntity(
                baseUrl,
                new HttpEntity<>("{\"latitude\":43.5855,\"longitude\":39.7231}", headers),
                MotoDto.class
        );

        assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        MotoDto createdMoto = Objects.requireNonNull(createdResponse.getBody());
        assertThat(createdMoto.id()).isNotNull();
        assertThat(createdMoto.lat()).isEqualTo(43.5855F);
        assertThat(createdMoto.lon()).isEqualTo(39.7231F);
    }

    @Test
    void shouldUpdateMotoPointFromJsonBody() {
        String baseUrl = "http://localhost:" + port + "/moto";

        ResponseEntity<MotoDto> createdResponse = restTemplate.postForEntity(
                baseUrl,
                new MotoDto(null, 55.7522F, 37.6156F),
                MotoDto.class
        );
        MotoDto createdMoto = Objects.requireNonNull(createdResponse.getBody());

        ResponseEntity<MotoDto> updatedResponse = restTemplate.exchange(
                baseUrl + "/" + createdMoto.id(),
                HttpMethod.PUT,
                new HttpEntity<>("{\"lat\":59.9386,\"lng\":30.3141}"),
                MotoDto.class
        );

        assertThat(updatedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        MotoDto updatedMoto = Objects.requireNonNull(updatedResponse.getBody());
        assertThat(updatedMoto.lat()).isEqualTo(59.9386F);
        assertThat(updatedMoto.lon()).isEqualTo(30.3141F);
    }
}
