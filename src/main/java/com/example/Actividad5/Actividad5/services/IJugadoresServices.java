package com.example.Actividad5.Actividad5.services;

import com.example.Actividad5.Actividad5.entities.Jugador;

import java.sql.SQLException;
import java.util.List;

public interface IJugadoresServices<Jugador> {

    public List<Jugador> getAll();
    public List<Jugador> getById(Long id);

    public Jugador save(Jugador jugador);

    public Jugador update(Long id,Jugador jugador);
}
