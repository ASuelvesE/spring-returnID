package com.example.Actividad5.Actividad5.conexion;

import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.entities.Entrenamiento;
import com.example.Actividad5.Actividad5.entities.Jugador;

import java.util.ArrayList;
import java.util.List;

public class Ram {

    List<Jugador> jugadores;
    List<Ejercicio> ejercicios;
    List<Entrenamiento> entrenamientos;
    private static Ram ram;

    private Ram(){
        jugadores = new ArrayList<>();
        ejercicios = new ArrayList<>();
        entrenamientos = new ArrayList<>();
    }

    public static Ram getInstance() {
        if(ram == null){
            ram = new Ram();
            return ram;
        }
        return ram;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public List<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }
}
