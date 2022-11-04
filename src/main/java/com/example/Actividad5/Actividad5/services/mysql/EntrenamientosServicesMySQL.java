package com.example.Actividad5.Actividad5.services.mysql;

import com.example.Actividad5.Actividad5.conexion.MySql;
import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.entities.Entrenamiento;
import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.entities.enums.Recuperacion;
import com.example.Actividad5.Actividad5.entities.enums.Resistencia;
import com.example.Actividad5.Actividad5.entities.enums.Velocidad;
import com.example.Actividad5.Actividad5.services.IEntrenamientosServices;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntrenamientosServicesMySQL implements IEntrenamientosServices<Entrenamiento> {
    @Override
    public List<Entrenamiento> getAll() {
        List<Entrenamiento> lista = new ArrayList<>();
        try{
            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT * FROM entrenamientos;");
            while(rs.next()){
                Entrenamiento e = new Entrenamiento(rs.getDate("fecha"),null,null,null);
                e.setId(rs.getLong("id"));
                lista.add(e);
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return lista;
    }

    @Override
    public Entrenamiento getById(Long id) {
        Entrenamiento en = null;
        try {
            en = findEntrenamientoById(id);
            String query = "SELECT * FROM jugadores WHERE id in ("+
                    "SELECT id_jugador FROM reservaEntrenamientos WHERE id_entrenamiento ="+en.getId()+");";
            ResultSet js = MySql.getInstance().createStatement().executeQuery(query);
            while (js.next()){
                Jugador j = new Jugador(js.getString("dni"),js.getString("nombre"),js.getString("apellidos"),js.getDate("fechaNacimiento"),
                        Resistencia.valueOf(Resistencia.getValor(js.getInt("resistencia"))),Velocidad.valueOf(Velocidad.getValor(js.getInt("velocidad"))),
                        Recuperacion.valueOf(Recuperacion.getValor(js.getInt("recuperacion"))));
                j.setId(js.getLong("id"));
                en.getAsistentes().add(j);
            }
            query = "SELECT * FROM ejercicios WHERE id in (SELECT id_ejercicio FROM entrenamientos_ejercicios " +
                    "WHERE id_entrenamiento ="+id+");";
            ResultSet ejs = MySql.getInstance().createStatement().executeQuery(query);
            while (ejs.next()){
                Ejercicio ej = new Ejercicio(ejs.getString("titulo"),ejs.getString("descripcion"),new ArrayList<>(),ejs.getString("duracion"),
                        new HashMap<String,String>(),new ArrayList<>(),new HashMap<String,String>());
                ej.getDureza().put("resistencia",Resistencia.getValor(ejs.getInt("resistencia")));
                ej.getDureza().put("velocidad",Velocidad.getValor(ejs.getInt("velocidad")));
                ej.getDureza().put("recuperacion",Recuperacion.getValor(ejs.getInt("recuperacion")));
                ej.setId(ejs.getLong("id"));
                ej.setDurezaMedia(ejs.getLong("dureza"));
                en.getEjercicios().add(ej);
            }
            if(en != null){
                for(Ejercicio ej : en.getEjercicios()){
                    ResultSet et = MySql.getInstance().createStatement().executeQuery("SELECT descripcion FROM etiquetas WHERE id_ejercicio= "+ej.getId()+";");
                    while (et.next()){
                        ej.getEtiquetas().add(et.getString("descripcion"));
                    }
                    ResultSet mat = MySql.getInstance().createStatement().executeQuery("SELECT descripcion FROM materiales WHERE id_ejercicio= "+ej.getId()+";");
                    while (mat.next()){
                        ej.getMateriales().add(mat.getString("descripcion"));
                    }
                    ResultSet req = MySql.getInstance().createStatement().executeQuery("SELECT * FROM recursosMultimedia WHERE id_ejercicio= "+ej.getId()+";");
                    while (req.next()){
                        ej.getRecursosMultimedia().put(req.getString("clave"), req.getString("valor"));
                    }
                }
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }

        return en;
    }

    @Override
    public Entrenamiento findEntrenamientoById(Long id) {
        Entrenamiento en = null;
        try {
            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT * FROM entrenamientos WHERE id="+id+";");
            while(rs.next()){
                en = new Entrenamiento(rs.getDate("fecha"),new ArrayList<>(),new ArrayList<>(),rs.getLong("durezaMedia"));
                en.setId(rs.getLong("id"));
            }
        }catch
        (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return en;
    }

    @Override
    public Entrenamiento save(Entrenamiento en) {
        try {
            en.calculaDurezaMedia();

            String pattern = "yyyy-MM-dd' 'HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String fechaFormat = simpleDateFormat.format(en.getFecha());

            String query = "INSERT INTO entrenamientos (durezaMedia,fecha) VALUES ("+
                    en.getDurezaMedia()+",'"+fechaFormat+"');";

            PreparedStatement ps = MySql.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                en.setId(new Long(rs.getInt(1)));
            }

            for(Ejercicio e : en.getEjercicios()){
                e.calculaDurezaMedia();
                int resistencia = Resistencia.valueOf(e.getDureza().get("resistencia")).getNumero();
                int velocidad = Velocidad.valueOf(e.getDureza().get("velocidad")).getNumero();
                int recuperacion = Recuperacion.valueOf(e.getDureza().get("recuperacion")).getNumero();
                query = "INSERT INTO ejercicios VALUES ('"+e.getTitulo()+"','"+
                        e.getDescripcion()+"','"+e.getDuracion()+"',"+resistencia+","+velocidad+
                        ","+recuperacion+","+e.getDurezaMedia()+");";
                ps = MySql.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.execute();
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    e.setId(new Long(rs.getInt(1)));
                }
                query = "INSERT INTO entrenamientos_ejercicios VALUES("+e.getId()+","+en.getId()+");";
                MySql.getInstance().createStatement().execute(query);
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

            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return en;
    }

    @Override
    public Entrenamiento update(Long id, List<Jugador> asistentes) {
        Entrenamiento resultado = null;
        try {
            MySql.getInstance().createStatement().execute("DELETE FROM reservaEntrenamientos WHERE id_entrenamiento= "+id+";");
            for(Jugador j : asistentes){
                String query = "INSERT INTO reservaEntrenamientos VALUES ("+j.getId()+","+id+");";
                MySql.getInstance().createStatement().execute(query);
            }
            /////////////////////////////////// AQUI DEBAJO RECOGEMOS LO QUE DEVUELVE ///////////////////////////////////////////////////////////
            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT * FROM entrenamientos WHERE id="+id+";");
            while(rs.next()){
                resultado = new Entrenamiento(rs.getDate("fecha"),new ArrayList<>(),new ArrayList<>(),rs.getLong("durezaMedia"));
                resultado.setId(rs.getLong("id"));
                resultado.setDurezaMedia(rs.getLong("durezaMedia"));
            }
            String query = "SELECT * FROM jugadores WHERE id in ("+
                    "SELECT id_jugador FROM reservaEntrenamientos WHERE id_entrenamiento ="+id+");";
            ResultSet js = MySql.getInstance().createStatement().executeQuery(query);
            while (js.next()){
                Jugador j = new Jugador(js.getString("dni"),js.getString("nombre"),js.getString("apellidos"),js.getDate("fechaNacimiento"),
                        Resistencia.valueOf(Resistencia.getValor(js.getInt("resistencia"))),Velocidad.valueOf(Velocidad.getValor(js.getInt("velocidad"))),
                        Recuperacion.valueOf(Recuperacion.getValor(js.getInt("recuperacion"))));
                j.setId(js.getLong("id"));
                resultado.getAsistentes().add(j);
            }
            query = "SELECT * FROM ejercicios WHERE id in (SELECT id_ejercicio FROM entrenamientos_ejercicios " +
                    "WHERE id_entrenamiento ="+id+");";
            ResultSet ejs = MySql.getInstance().createStatement().executeQuery(query);
            while (ejs.next()){
                Ejercicio ej = new Ejercicio(ejs.getString("titulo"),ejs.getString("descripcion"),new ArrayList<>(),ejs.getString("duracion"),
                        new HashMap<String,String>(),new ArrayList<>(),new HashMap<String,String>());
                ej.getDureza().put(Resistencia.getValor(ejs.getInt("resistencia")),Resistencia.getValor(ejs.getInt("resistencia")));
                ej.getDureza().put(Velocidad.getValor(ejs.getInt("velocidad")),Velocidad.getValor(ejs.getInt("velocidad")));
                ej.getDureza().put(Recuperacion.getValor(ejs.getInt("recuperacion")),Recuperacion.getValor(ejs.getInt("recuperacion")));
                ej.setId(ejs.getLong("id"));
                ej.setDurezaMedia(ejs.getLong("dureza"));

                ResultSet et = MySql.getInstance().createStatement().executeQuery("SELECT descripcion FROM etiquetas WHERE id_ejercicio= "+id+";");
                while (et.next()){
                    ej.getEtiquetas().add(et.getString("descripcion"));
                }
                ResultSet mat = MySql.getInstance().createStatement().executeQuery("SELECT descripcion FROM materiales WHERE id_ejercicio= "+id+";");
                while (mat.next()){
                    ej.getMateriales().add(mat.getString("descripcion"));
                }
                ResultSet req = MySql.getInstance().createStatement().executeQuery("SELECT * FROM recursosMultimedia WHERE id_ejercicio= "+id+";");
                while (req.next()){
                    ej.getRecursosMultimedia().put(req.getString("clave"), req.getString("valor"));
                }
                resultado.getEjercicios().add(ej);
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return resultado;
    }


    @Override
    public List<Entrenamiento> delete(Long id) {
        List<Entrenamiento> lista = new ArrayList<>();
        try {
            MySql.getInstance().createStatement().execute("DELETE FROM reservaEntrenamientos WHERE id_entrenamiento="+id+";");
            MySql.getInstance().createStatement().execute("DELETE FROM entrenamientos_ejercicios WHERE id_entrenamiento="+id+";");
            MySql.getInstance().createStatement().execute("DELETE FROM entrenamientos WHERE id="+id+";");

            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT * FROM entrenamientos;");
            while(rs.next()){
                Entrenamiento e = new Entrenamiento(rs.getDate("fecha"),null,null,null);
                e.setId(rs.getLong("id"));
                lista.add(e);
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return lista;
    }
}
