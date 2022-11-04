package com.example.Actividad5.Actividad5.services.ram;

import com.example.Actividad5.Actividad5.conexion.Ram;
import com.example.Actividad5.Actividad5.entities.Entrenamiento;
import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.services.IJugadoresServices;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JugadoresServicesRAM implements IJugadoresServices<Jugador> {
    @Override
    public List<Jugador> getAll() {
        List<Jugador> jugadores = Ram.getInstance().getJugadores();
        List<Jugador> lista = new ArrayList<>();
        for(Jugador j : jugadores){
            Jugador nuevo = new Jugador(j.getDni(),j.getNombre(),j.getApellidos(),null,null,null,null);
            Jugador.contador--;
            nuevo.setId(j.getId());
            lista.add(nuevo);
        }
        return lista;
    }

    @Override
    public List<Jugador> getById(Long id) {
        List<Jugador> salida = new ArrayList<>();
        List<Jugador> jugadores = Ram.getInstance().getJugadores();
        for(Jugador j : jugadores){
            if(j.getId() == id){
                salida.add(j);
                return salida;
            }
        }
        return null;
    }


    @Override
    public Jugador save(Jugador jugador) {
        Ram.getInstance().getJugadores().add(jugador);
        return jugador;
    }


    @Override
    public Jugador update(Long id,Jugador jugador) {
        Iterator it = Ram.getInstance().getJugadores().iterator();
        while(it.hasNext()){
            Jugador j = (Jugador) it.next();
            if(j.getId() == id){
                it.remove();
                jugador.setId(id);
                Ram.getInstance().getJugadores().add(jugador);
                return jugador;
            }
        }
        return null;
    }
}
