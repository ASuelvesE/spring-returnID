package com.example.Actividad5.Actividad5.entities;

import com.example.Actividad5.Actividad5.entities.enums.Recuperacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Ejercicio implements Serializable {

    public static Long contador = 1L;
    private Long id;
    private String titulo;
    private String descripcion;
    private List<String> etiquetas;
    private String duracion;
    private HashMap<String,String> dureza;
    private Long durezaMedia;
    private List<String> materiales;
    private HashMap<String,String> recursosMultimedia;

    public Ejercicio(String titulo, String descripcion, List<String> etiquetas, String duracion, HashMap<String,String> dureza, List<String> materiales, HashMap<String, String> recursosMultimedia) {
        this.id = contador;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etiquetas = etiquetas;
        this.duracion = duracion;
        this.dureza = dureza;
        this.durezaMedia = 0L;
        this.materiales = materiales;
        this.recursosMultimedia = recursosMultimedia;
        this.calculaDurezaMedia();
        contador++;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<String> getEtiquetas() {
        return etiquetas;
    }

    public String getDuracion() {
        return duracion;
    }

    public HashMap<String, String> getDureza() {
        return dureza;
    }

    public List<String> getMateriales() {
        return materiales;
    }

    public HashMap<String, String> getRecursosMultimedia() {
        return recursosMultimedia;
    }

    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void setDureza(HashMap<String, String> dureza) {
        this.dureza = dureza;
    }

    public void setMateriales(List<String> materiales) {
        this.materiales = materiales;
    }

    public void setRecursosMultimedia(HashMap<String, String> recursosMultimedia) {
        this.recursosMultimedia = recursosMultimedia;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", etiquetas=" + etiquetas +
                ", duracion='" + duracion + '\'' +
                ", dureza=" + dureza +
                ", materiales=" + materiales +
                ", recursosMultimedia=" + recursosMultimedia +
                '}';
    }
    public static void reseteaContador(){
        contador = 1L;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDurezaMedia() {
        return durezaMedia;
    }

    public void calculaDurezaMedia(){
        int sumaDurezas = 0;
        if(!dureza.isEmpty() && dureza != null){
            Iterator it = this.dureza.values().stream().iterator();
            while (it.hasNext()){
                String valor = (String) it.next();
                sumaDurezas += Recuperacion.valueOf(valor).getNumero();
            }
            this.durezaMedia = Long.valueOf(sumaDurezas/this.dureza.size());
        }
    }

    public void setDurezaMedia(long dureza) {
        this.durezaMedia = dureza;
    }
}
