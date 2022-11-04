package com.example.Actividad5.Actividad5.services.mysql;

import com.example.Actividad5.Actividad5.conexion.MySql;
import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.entities.enums.Recuperacion;
import com.example.Actividad5.Actividad5.entities.enums.Resistencia;
import com.example.Actividad5.Actividad5.entities.enums.Velocidad;
import com.example.Actividad5.Actividad5.services.IJugadoresServices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JugadoresServicesMySQL implements IJugadoresServices<Jugador> {
    @Override
    public List<Jugador> getAll() {
        List<Jugador> lista = new ArrayList<>();
        try {
            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT * FROM jugadores;");
            while(rs.next()){
                String res = Resistencia.getValor(rs.getInt("resistencia"));
                String vel = Resistencia.getValor(rs.getInt("velocidad"));
                String rec = Resistencia.getValor(rs.getInt("recuperacion"));

                Jugador j = new Jugador(rs.getString("dni"),rs.getString("nombre"),rs.getString("apellidos"),
                        null,null,null,null);
                j.setId(rs.getLong("id"));
                lista.add(j);
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return lista;
    }

    @Override
    public List<Jugador> getById(Long id) {
        List<Jugador> lista = new ArrayList<>();
        try {
            ResultSet rs = MySql.getInstance().createStatement().executeQuery("SELECT * FROM jugadores WHERE id ='" + id +"';");
            while(rs.next()){
                String res = Resistencia.getValor(rs.getInt("resistencia"));
                String vel = Resistencia.getValor(rs.getInt("velocidad"));
                String rec = Resistencia.getValor(rs.getInt("recuperacion"));

                Jugador j = new Jugador(rs.getString("dni"),rs.getString("nombre"),rs.getString("apellidos"),
                        rs.getDate("fechaNacimiento"),Resistencia.valueOf(res),
                        Velocidad.valueOf(vel),Recuperacion.valueOf(rec));
                j.setId(rs.getLong("id"));
                lista.add(j);
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return lista;
    }

    @Override
    public Jugador save(Jugador j) {
        try {
            String query = "INSERT INTO jugadores(dni,nombre,apellidos,fechaNacimiento,resistencia,velocidad,recuperacion) VALUES(" +
                    "'" + j.getDni()+"',"+"'"+j.getNombre()+"',"+"'"+j.getApellidos()+
                    "','"+j.getFechaNacimiento()+"',"+j.getResistencia().getNumero()+
                    ","+j.getVelocidad().getNumero()+","+j.getRecuperacion().getNumero()+");";
            PreparedStatement ps = MySql.getInstance().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                j.setId(new Long(rs.getInt(1)));
            }
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return j;
    }

    @Override
    public Jugador update(Long id, Jugador j) {
        try {
            String query = "UPDATE jugadores SET " +
                    "dni="+"'" + j.getDni()+"',"+"nombre="+"'" + j.getNombre()+"',"+"apellidos="+"'" + j.getApellidos()+"',"+
                    "fechaNacimiento="+"'" + j.getFechaNacimiento()+"',"+"resistencia="+j.getResistencia().getNumero()+","+
                    "velocidad="+j.getVelocidad().getNumero()+","+"recuperacion="+j.getRecuperacion().getNumero()+" WHERE id="+id+";";
            MySql.getInstance().createStatement().execute(query);
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
        return j;
    }
}
