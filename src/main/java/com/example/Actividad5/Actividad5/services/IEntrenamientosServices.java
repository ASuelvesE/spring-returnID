package com.example.Actividad5.Actividad5.services;

import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.entities.Entrenamiento;
import com.example.Actividad5.Actividad5.entities.Jugador;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IEntrenamientosServices<Entrenamiento> {

    public List<Entrenamiento> getAll();
    public Entrenamiento getById(Long id);
    public Entrenamiento findEntrenamientoById(Long id);
    public Entrenamiento save(Entrenamiento e);
    public Entrenamiento update(Long id,List<Jugador> asistentes);
    public List<Entrenamiento> delete(Long id);
}
