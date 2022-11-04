package com.example.Actividad5.Actividad5.entities.enums;

public enum Velocidad {
    HIGH(3),MEDIUM(2),LOW(1);
    private  final int numero;

    Velocidad(int numero){
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }
    public static String getValor(int numero){
        if(numero == 3) return "HIGH";
        if(numero == 2) return "MEDIUM";
        if(numero == 1) return "LOW";
        return "DESCONOCIDO";
    }
}

