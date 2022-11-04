package com.example.Actividad5.Actividad5.services.mysql;

import com.example.Actividad5.Actividad5.conexion.MySql;
import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.entities.enums.Recuperacion;
import com.example.Actividad5.Actividad5.entities.enums.Resistencia;
import com.example.Actividad5.Actividad5.entities.enums.Velocidad;
import com.example.Actividad5.Actividad5.services.IEjerciciosServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class EjerciciosServicesMySQL implements IEjerciciosServices<Ejercicio> {
    @Override
    public List<Ejercicio> getAll() {
        List<Ejercicio> lista = new ArrayList<>();
        try {
            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT id,titulo FROM ejercicios;");
            while (rs.next()) {
                Ejercicio e = new Ejercicio(rs.getString("titulo"), null, new ArrayList<>(), null, new HashMap<>(), new ArrayList<>(), new HashMap<>());
                e.setId(rs.getLong("id"));
                lista.add(e);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return lista;
    }

    @Override
    public Ejercicio getById(Long id) {
        Ejercicio e = null;
        try{
            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT * FROM ejercicios WHERE id= "+ id+";");
            HashMap<String,String> dureza = new HashMap<>();
            while(rs.next()){
                dureza.put("resistencia", Resistencia.getValor(rs.getInt("resistencia")));
                dureza.put("velocidad", Velocidad.getValor(rs.getInt("velocidad")));
                dureza.put("recuperacion", Recuperacion.getValor(rs.getInt("recuperacion")));
                e = new Ejercicio(rs.getString("titulo"),rs.getString("descripcion"),new ArrayList<>(),rs.getString("duracion"),dureza,new ArrayList<>(),new HashMap<String,String>());
                e.setId(rs.getLong("id"));
                e.setDurezaMedia(rs.getLong("dureza"));
            }
            e.setEtiquetas(getEtiquetasFrom(e));
            e.setMateriales(getMaterialesFrom(e));
            e.setRecursosMultimedia(getRecursosMultimediaFrom(e));
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return e;
    }

    @Override
    public List<String> getEtiquetasFrom(Ejercicio ejercicio) {
        List<String> etiquetas = new ArrayList<>();
        ResultSet et = null;
        try {
            et = MySql.getInstance().createStatement().executeQuery("SELECT descripcion FROM etiquetas WHERE id_ejercicio= "+ejercicio.getId()+";");
            while (et.next()){
                etiquetas.add(et.getString("descripcion"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return etiquetas;
    }

    @Override
    public List<String> getMaterialesFrom(Ejercicio ejercicio) {
        try {
            ResultSet mat = MySql.getInstance().createStatement().executeQuery("SELECT descripcion FROM materiales WHERE id_ejercicio= "+ejercicio.getId()+";");
            while (mat.next()){
                ejercicio.getMateriales().add(mat.getString("descripcion"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ejercicio.getMateriales();
    }

    @Override
    public HashMap<String, String> getRecursosMultimediaFrom(Ejercicio ejercicio) {
        HashMap<String,String> hasmap = new HashMap<>();
        try {
            ResultSet req = MySql.getInstance().createStatement().executeQuery("SELECT * FROM recursosMultimedia WHERE id_ejercicio= "+ejercicio.getId()+";");
            while (req.next()){
                hasmap.put(req.getString("clave"), req.getString("valor"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasmap;
    }

    @Override
    public Ejercicio save(Ejercicio e) {
        try{
            e.calculaDurezaMedia();
            int resistencia = Resistencia.valueOf(e.getDureza().get("resistencia")).getNumero();
            int velocidad = Velocidad.valueOf(e.getDureza().get("velocidad")).getNumero();
            int recuperacion = Recuperacion.valueOf(e.getDureza().get("recuperacion")).getNumero();
            String query = "INSERT INTO ejercicios(titulo,descripcion,duracion,resistencia,velocidad,recuperacion,dureza) VALUES('"+e.getTitulo()+"','"+
                    e.getDescripcion()+"','"+e.getDuracion()+"','"+resistencia+"','"+ velocidad+
                    "','"+recuperacion+"',"+e.getDurezaMedia()+");";
            PreparedStatement ps = MySql.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                e.setId(new Long(rs.getInt(1)));
            }
            for(String mat : e.getMateriales()){
                query = "INSERT INTO materiales VALUES("+e.getId()+",'"+mat+"');";
                MySql.getInstance().createStatement().execute(query);
            }
            for(String et : e.getEtiquetas()){
                query = "INSERT INTO etiquetas VALUES("+e.getId()+",'"+et+"');";
                MySql.getInstance().createStatement().execute(query);
            }
            for(String key : e.getRecursosMultimedia().keySet()){
                String clave = key;
                String valor = e.getRecursosMultimedia().get(clave);
                query = "INSERT INTO recursosMultimedia VALUES("+e.getId()+",'"+clave+"','"+valor+"');";
                MySql.getInstance().createStatement().execute(query);
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return e;
    }
}
