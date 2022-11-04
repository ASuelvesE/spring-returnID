package com.example.Actividad5.Actividad5.services;

import com.example.Actividad5.Actividad5.entities.Ejercicio;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IEjerciciosServices <Ejercicio> {

    public List<Ejercicio> getAll();
    public Ejercicio getById(Long id);
    public List<String> getEtiquetasFrom(Ejercicio ejercicio);
    public List<String> getMaterialesFrom(Ejercicio ejercicio);
    public HashMap<String,String> getRecursosMultimediaFrom(Ejercicio ejercicio);
    public Ejercicio save(Ejercicio ejercicio);
}
