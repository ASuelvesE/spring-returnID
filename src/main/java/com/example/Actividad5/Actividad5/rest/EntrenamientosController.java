package com.example.Actividad5.Actividad5.rest;

import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.entities.Entrenamiento;
import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.services.mysql.EntrenamientosServicesMySQL;
import com.example.Actividad5.Actividad5.services.ram.EntrenamientosServicesRAM;
import com.example.Actividad5.Actividad5.services.IEntrenamientosServices;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EntrenamientosController {

    IEntrenamientosServices<Entrenamiento> services;

    public EntrenamientosController(){
        this.services = new EntrenamientosServicesMySQL();
    }
    @GetMapping("/entrenamientos")
    List<Entrenamiento> getAll() throws SQLException {
        try{
            return services.getAll();
        }catch (RuntimeException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    @GetMapping("/entrenamientos/{id}")
    Entrenamiento getById(@PathVariable Long id) throws SQLException {
        try{
            return services.getById(id);
        }catch (RuntimeException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    @PostMapping(value = "/entrenamientos",produces = MediaType.APPLICATION_JSON_VALUE)
    Entrenamiento save(@RequestBody Entrenamiento entrenamiento) {
            return services.save(entrenamiento);
    }
    @PutMapping(value = "/entrenamientos/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    Entrenamiento update(@PathVariable Long id, @RequestBody List<Jugador> asistentes) {
            return services.update(id,asistentes);
    }
    @DeleteMapping("/entrenamientos/{id}")
    List<Entrenamiento> delete(@PathVariable Long id) {
            return services.delete(id);
    }

}
