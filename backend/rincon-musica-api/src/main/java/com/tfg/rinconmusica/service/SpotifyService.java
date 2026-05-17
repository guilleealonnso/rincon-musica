package com.tfg.rinconmusica.service;

import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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

    public String buscarIdSpotifyCancion(String titulo, String artista, String accessToken) {
        String consulta = titulo + " " + artista;
        return buscarEnSpotify(consulta, accessToken);
    }

    private String buscarEnSpotify(String consulta, String accessToken) {
        String url = "https://api.spotify.com/v1/search?q={consulta}&type=track&limit=1&market=ES";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Map.class,
                    consulta
            );

            if (response.getBody() == null) {
                return null;
            }

            Map tracks = (Map) response.getBody().get("tracks");

            if (tracks == null) {
                return null;
            }

            java.util.List items = (java.util.List) tracks.get("items");

            if (items == null || items.isEmpty()) {
                return null;
            }

            Map primerResultado = (Map) items.get(0);
            Object id = primerResultado.get("id");

            if (id == null) {
                return null;
            }

            return id.toString();

        } catch (Exception e) {
            System.out.println("Error buscando canción en Spotify: " + consulta);
            System.out.println(e.getMessage());
            return null;
        }
    }
}