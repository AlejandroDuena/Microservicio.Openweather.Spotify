package com.example.microservicio.Servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioOpenWeather {
    @Value("${openweather.apikey}")
    String apikey;
    double temp;
    public double buscarTempPorCiudad (String ciudad) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+ ciudad +"&appid=" + apikey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException(root.asText());

        }
        JsonNode main = root.path("main");
        JsonNode tempOpen = main.path("temp");
        double temp = tempOpen.asDouble()-273.15;
        this.temp = temp;
        System.out.println(temp);
        return temp;
    }

    public double buscarTempPorLongitudYLatitud (double longitud, double latitud) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+ latitud + "&lon=" + longitud + "&appid=" + apikey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        if (!response.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException(root.asText());
        }
        JsonNode main = root.path("main");
        JsonNode tempOpen = main.path("temp");
        double temp = tempOpen.asDouble()-273.15;
        this.temp = temp;
        System.out.println(temp);
        return temp;
    }
    public String optenerGenero (double temp){
        String genero = "";
        if (temp > 30){
            genero = "latino";
        }
        if(temp <= 30 && temp >= 15){
            genero = "pop";
        }
        if(temp < 15 && temp >= 10){
            genero = "rock";
        }
        if (temp < 10){
            genero = "classical";
        }
        return genero;
    }
}