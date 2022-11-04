package com.example.Actividad5.Actividad5.rest;

import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.services.mysql.EjerciciosServicesMySQL;
import com.example.Actividad5.Actividad5.services.ram.EjerciciosServicesRAM;
import com.example.Actividad5.Actividad5.services.IEjerciciosServices;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EjerciciosController {

    IEjerciciosServices<Ejercicio> services;

    EjerciciosController(){
        this.services = new EjerciciosServicesMySQL();
    }

    @GetMapping("/ejercicios")
    List<Ejercicio> getAll() {
        return services.getAll();
    }
    @GetMapping("/ejercicios/{id}")
    Ejercicio getById(@PathVariable Long id) {
            return services.getById(id);
    }
    @PostMapping(value = "/ejercicios",produces = MediaType.APPLICATION_JSON_VALUE)
    Ejercicio save(@RequestBody Ejercicio ej) {
            return services.save(ej);
    }

}
