package com.example.Actividad5.Actividad5.entities;


import com.example.Actividad5.Actividad5.entities.enums.Resistencia;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Entrenamiento implements Serializable {
    public static Long contador = 1L;
    private Long id;
    private Date fecha;
    private List<Ejercicio> ejercicios = new ArrayList<>();
    private List<Jugador> asistentes = new ArrayList<>();
    private Long durezaMedia;


    public Entrenamiento(Date fecha, List<Ejercicio> ejercicios, List<Jugador> asistentes, Long durezaMedia) {
        this.id = contador;
        this.fecha = fecha;
        this.ejercicios = ejercicios;
        this.asistentes = asistentes;
        this.durezaMedia = durezaMedia;
        contador++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public List<Jugador> getAsistentes() {
        return asistentes;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public void setAsistentes(List<Jugador> asistentes) {
        this.asistentes = asistentes;
    }
    public void calculaDurezaMedia(){
        int sumaCondicionesFisicas = 0;
        for(Ejercicio e : this.ejercicios){
            HashMap<String,String> dureza = e.getDureza();
            Iterator it = dureza.values().iterator();
            while (it.hasNext()){
                sumaCondicionesFisicas += Resistencia.valueOf((String)it.next()).getNumero();
            }
        }
        this.durezaMedia = Long.valueOf(sumaCondicionesFisicas/(this.ejercicios.size() * 3));
    }

    public void setDurezaMedia(Long durezaMedia) {
        this.durezaMedia = durezaMedia;
    }

    public Long getDurezaMedia() {
        return durezaMedia;
    }

    @Override
    public String toString() {
        return "Entrenamiento{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", ejercicios=" + ejercicios +
                ", asistentes=" + asistentes +
                '}';
    }
    public static void reseteaContador(){
        contador = 1L;
    }
}