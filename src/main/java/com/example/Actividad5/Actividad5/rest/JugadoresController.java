package com.example.Actividad5.Actividad5.rest;

import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.services.IJugadoresServices;
import com.example.Actividad5.Actividad5.services.mysql.JugadoresServicesMySQL;
import com.example.Actividad5.Actividad5.services.ram.JugadoresServicesRAM;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class JugadoresController {

    IJugadoresServices<Jugador> services;

    public JugadoresController(){
        services = new JugadoresServicesMySQL();
    }

    @GetMapping("/jugadores")
    List<Jugador> getAll() {
            return services.getAll();
    }
    @GetMapping("/jugadores/{id}")
    List<Jugador> getById(@PathVariable Long id)  {
            return services.getById(id);
    }
    @PostMapping(value = "/jugadores",produces = MediaType.APPLICATION_JSON_VALUE)
    Jugador save(@RequestBody Jugador jugador) {
            return services.save(jugador);
    }
    @PutMapping(value = "/jugadores/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    Jugador update(@PathVariable Long id,@RequestBody Jugador jugador) {
            return services.update(id,jugador);
    }
}
