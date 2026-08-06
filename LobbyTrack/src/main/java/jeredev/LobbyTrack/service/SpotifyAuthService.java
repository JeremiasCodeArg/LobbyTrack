package jeredev.LobbyTrack.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SpotifyAuthService {
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private String tokenVigente;

    private final RestTemplate restTemplate = new RestTemplate();


    @PostConstruct
    public void obtenerAccessToken(){
        String url = "https://accounts.spotify.com/api/token";

        //sobre de la carta
        HttpHeaders headers = new HttpHeaders();

        /*Le avisa a spotify, eque el formato adentro de la carta. MediaTpye contiene todos los formas que existen
        en internet.
        */
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //Spring lo encripta
        headers.setBasicAuth(clientId, clientSecret);

        /*
            grant_type <- sistema de seguridad que usa Spotify
            cliente_credentials <- Le avisa al Spotify que es un servidor y no esta intentando inciiar en nombre de cuenta
            en particular.
         */
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        response.ge


        System.out.println("Respuesta de Spotify: " + response.getBody());

    }
}
