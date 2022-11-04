package com.example.Actividad5.Actividad5.entities;

import com.example.Actividad5.Actividad5.entities.enums.Recuperacion;
import com.example.Actividad5.Actividad5.entities.enums.Resistencia;
import com.example.Actividad5.Actividad5.entities.enums.Velocidad;

import java.io.Serializable;
import java.sql.Date;

public class Jugador implements Serializable {

    public static Long contador = 1L;
    private Long id;
    private String dni;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private Resistencia resistencia;
    private Velocidad velocidad;
    private Recuperacion recuperacion;

    public Jugador(String dni, String nombre, String apellidos, Date fechaNacimiento, Resistencia resistencia, Velocidad velocidad, Recuperacion recuperacion) {
        this.id = contador;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.resistencia = resistencia;
        this.velocidad = velocidad;
        this.recuperacion = recuperacion;
        contador++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Resistencia getResistencia() {
        return resistencia;
    }

    public Velocidad getVelocidad() {
        return velocidad;
    }

    public Recuperacion getRecuperacion() {
        return recuperacion;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", resistencia=" + resistencia +
                ", velocidad=" + velocidad +
                ", recuperacion=" + recuperacion +
                '}';
    }
    public static void reseteaContador(){
        contador = 1L;
    }
}
