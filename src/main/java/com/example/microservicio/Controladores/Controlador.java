package com.example.microservicio.Controladores;

import com.example.microservicio.Servicios.ServicioOpenWather;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controlador {
    private static final Logger LOGGER = LoggerFactory.getLogger(Controlador.class);
    ServicioOpenWather servOpenWather;
    public Controlador (ServicioOpenWather servOpenWather){
        this.servOpenWather = servOpenWather;
    }
    @GetMapping("Microservicio")
    public ResponseEntity<Object> temperatura (@RequestParam("ciu") String ciudad) throws JsonProcessingException {
        Object ConteOpenWather = servOpenWather.buscarTemperatura(ciudad);
        return ResponseEntity.ok(ConteOpenWather);
    }
}
