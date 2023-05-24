package com.API.Microservicio.Servicios;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ServicioSpotify {
    @Value("${spotify.clientID}")
    String clientid = "CLIENT_ID";
    @Value("${spotify.clientSecret}")
    String clientsecret = "CLIENT_SECRET";
    @Autowired
    ServicioOpenWeather servicioOpenWeather;
    public String conversionABase64 (){
        String clients = clientid + ":" + clientsecret;
        return Base64.getEncoder().encodeToString(clients.getBytes());
    }

    public String optenerToken () throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        String url = "https://accounts.spotify.com/api/token";
        headers.set("Authorization","Basic " + conversionABase64());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode access_token = root.path("access_token");
        return access_token.asText();
    }
    public List<String> tracks (double temp) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        String genero = servicioOpenWeather.optenerGenero(temp);
        String url = "https://api.spotify.com/v1/search?q=genre:" + genero + "&type=track";
        headers.set("Authorization", "Bearer " + optenerToken());
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        JsonNode root = mapper.readTree(response.getBody()).path("tracks").path("items");
        Iterator<JsonNode> iterator = root.elements();
        List<String> canciones = new ArrayList();
        while (iterator.hasNext()){
            JsonNode object = iterator.next();
            JsonNode name = object.path("name");
            canciones.add(name.asText());
        }
        /*JsonNode tracks = root.path("tracks");
        JsonNode items = tracks.path("items");
        JsonNode name = items.path("name");
        System.out.println(name.asText());
        System.out.println(root.asText());
        return canciones;*/
        return canciones;
    }
}
