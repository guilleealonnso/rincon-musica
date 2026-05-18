package com.tfg.rinconmusica.service;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String obtenerAccessToken() {
        String url = "https://accounts.spotify.com/api/token";

        String credenciales = clientId + ":" + clientSecret;
        String credencialesBase64 = Base64.getEncoder()
                .encodeToString(credenciales.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + credencialesBase64);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    url,
                    request,
                    Map.class
            );

            if (response.getBody() == null) {
                return null;
            }

            Object token = response.getBody().get("access_token");

            if (token == null) {
                return null;
            }

            return token.toString();

        } catch (Exception e) {
            System.out.println("Error obteniendo token de Spotify: " + e.getMessage());
            return null;
        }
    }

    public Integer obtenerPopularidadCancion(String idSpotify, String accessToken) {
        String url = "https://api.spotify.com/v1/tracks/" + idSpotify;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Map.class
            );

            if (response.getBody() == null) {
                return null;
            }

            Object popularidad = response.getBody().get("popularity");

            if (popularidad == null) {
                return null;
            }

            return (Integer) popularidad;

        } catch (Exception e) {
            System.out.println("Error obteniendo popularidad de Spotify para ID: " + idSpotify);
            System.out.println(e.getMessage());
            return null;
        }
    }
}