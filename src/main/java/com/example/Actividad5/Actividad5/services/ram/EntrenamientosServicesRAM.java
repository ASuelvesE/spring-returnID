package com.example.Actividad5.Actividad5.services.ram;

import com.example.Actividad5.Actividad5.conexion.Ram;
import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.entities.Entrenamiento;
import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.services.IEntrenamientosServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EntrenamientosServicesRAM implements IEntrenamientosServices<Entrenamiento> {
    @Override
    public List<Entrenamiento> getAll() {
        return Ram.getInstance().getEntrenamientos();
    }

    @Override
    public Entrenamiento getById(Long id) {
        for(Entrenamiento e : Ram.getInstance().getEntrenamientos()){
            if(e.getId() == id){
                e.calculaDurezaMedia();
                return e;
            }
        }
        return null;
    }

    @Override
    public Entrenamiento findEntrenamientoById(Long id) {
        return null;
    }

    @Override
    public Entrenamiento save(Entrenamiento e) {
        Entrenamiento nuevo = new Entrenamiento(e.getFecha(),e.getEjercicios(),new ArrayList<>(),null);
        Ram.getInstance().getEntrenamientos().add(nuevo);
        return nuevo;
    }

    @Override
    public Entrenamiento update(Long id, List<Jugador> asistentes) {
        for(Entrenamiento e : Ram.getInstance().getEntrenamientos()){
            if(e.getId() == id){
                e.setAsistentes(asistentes);
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Entrenamiento> delete(Long id) {
        Iterator it = Ram.getInstance().getEntrenamientos().iterator();
        while (it.hasNext()){
            Entrenamiento e = (Entrenamiento) it.next();
            if(e.getId() == id){
                it.remove();
                return Ram.getInstance().getEntrenamientos();
            }
        }
        return null;
    }
}
