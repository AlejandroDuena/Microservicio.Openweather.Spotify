package com.example.microservicio.Controladores;

import com.example.microservicio.Servicios.ServicioOpenWeather;
import com.example.microservicio.Servicios.ServicioSpotify;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controlador {
    ServicioOpenWeather servOpenWather;
    ServicioSpotify servSpotify;
    public Controlador (ServicioOpenWeather servOpenWather, ServicioSpotify servSpotify){
        this.servOpenWather = servOpenWather;
        this.servSpotify = servSpotify;
    }
    @GetMapping("Microservicio_por_ciudad")
    public ResponseEntity<Object> temperatura (@RequestParam("ciu") String ciudad) throws JsonProcessingException {
        try {
            double ConteOpenWather = servOpenWather.buscarTempPorCiudad(ciudad);
            Object ConteSpotify =servSpotify.tracks(ConteOpenWather);
            return ResponseEntity.ok(ConteSpotify);
        } catch (RuntimeException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @GetMapping(value = "Microservicio_por_longitud_y_latitud", params = {"lon","lat"})
    public ResponseEntity<Object> microservicio (@RequestParam("lon") double longitud, @RequestParam("lat") double lat) throws JsonProcessingException{
        double ConteOpenWather = servOpenWather.buscarTempPorLongitudYLatitud(longitud, lat);
        Object ConteSpotify = servSpotify.tracks(ConteOpenWather);
        return ResponseEntity.ok(ConteSpotify);
    }
}
