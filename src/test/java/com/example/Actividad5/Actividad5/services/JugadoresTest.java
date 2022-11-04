package com.example.Actividad5.Actividad5.services;

import com.example.Actividad5.Actividad5.conexion.Ram;
import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.services.ram.JugadoresServicesRAM;
import org.junit.Before;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JugadoresTest {

    IJugadoresServices<Jugador> services;

    public JugadoresTest(){
        services = new JugadoresServicesRAM();
    }

    @Before
    public void clean(){
        Ram.getInstance().getJugadores().clear();
        Jugador.reseteaContador();
        Ram.getInstance().getJugadores().add(new Jugador("123","nombreTest","apellidosTest",null,null,null,null));
        Ram.getInstance().getJugadores().add(new Jugador("456","nombreTest","apellidosTest",null,null,null,null));
        Ram.getInstance().getJugadores().add(new Jugador("789","nombreTest2","apellidosTest2",null,null,null,null));
    }

    @Test
    public void getAll() throws SQLException {
        assertEquals(3,services.getAll().size());
    }
    @Test
    public void getById() throws SQLException {
        assertEquals(1,services.getById(2L).size());
    }
    @Test
    public void save() throws SQLException {
        Jugador nuevo = services.save(new Jugador("111","nombreNuevo","apellidosTest",null,null,null,null));
        assertEquals(4,Ram.getInstance().getJugadores().size());
    }
    @Test
    public void update() throws SQLException {
        Jugador actualizado = services.update(2L,new Jugador("456","nombreActualizado","apellidosTest2",null,null,null,null));
        assertEquals("nombreActualizado",actualizado.getNombre());
    }

}
