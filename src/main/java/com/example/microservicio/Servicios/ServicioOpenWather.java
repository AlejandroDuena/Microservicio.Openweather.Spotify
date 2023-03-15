package com.example.microservicio.Servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServicioOpenWather {
    @Value("${openweather.apikey}")
    String apikey;
    public Object buscarTemperatura (String ciudad) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+ ciudad +"&appid=" + apikey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode main = root.path("main");
        JsonNode temp = main.path("temp");
        return temp;
    }
}