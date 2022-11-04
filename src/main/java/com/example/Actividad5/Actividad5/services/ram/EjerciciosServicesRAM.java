package com.example.Actividad5.Actividad5.services.ram;

import com.example.Actividad5.Actividad5.conexion.Ram;
import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.services.IEjerciciosServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EjerciciosServicesRAM implements IEjerciciosServices<Ejercicio> {
    @Override
    public List getAll() {
        List<Ejercicio> lista = new ArrayList<>();
        for(Ejercicio e : Ram.getInstance().getEjercicios()){
            Ejercicio nuevo = new Ejercicio(e.getTitulo(),null,new ArrayList<>(),null,new HashMap<String,String>(),new ArrayList<>(),new HashMap<String,String>());
            nuevo.setId(e.getId());
            lista.add(nuevo);
            Ejercicio.contador--;
        }
        return lista;
    }

    @Override
    public Ejercicio getById(Long id) {
        for(Ejercicio e : Ram.getInstance().getEjercicios()){
            if(e.getId() == id)
                return e;
        }
        return null;
    }

    @Override
    public List<String> getEtiquetasFrom(Ejercicio ejercicio) {
        return null;
    }

    @Override
    public List<String> getMaterialesFrom(Ejercicio ejercicio) {
        return null;
    }

    @Override
    public HashMap<String, String> getRecursosMultimediaFrom(Ejercicio ejercicio) {
        return null;
    }

    @Override
    public Ejercicio save(Ejercicio e) {
        Ram.getInstance().getEjercicios().add(e);
        return e;
    }
}
