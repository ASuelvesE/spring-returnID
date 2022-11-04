package com.example.Actividad5.Actividad5.services;

import com.example.Actividad5.Actividad5.conexion.Ram;
import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.services.ram.EjerciciosServicesRAM;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EjerciciosTest {

    IEjerciciosServices<Ejercicio> services;

    public EjerciciosTest(){
        this.services = new EjerciciosServicesRAM();
    }
    @Before
    public void clean(){
        Ram.getInstance().getEjercicios().clear();
        Ejercicio.reseteaContador();
        Ram.getInstance().getEjercicios().add(new Ejercicio("EjercicioTest",null,new ArrayList<>(),null,new HashMap<String,String>(),new ArrayList<>(),new HashMap<String,String>()));
        Ram.getInstance().getEjercicios().add(new Ejercicio("EjercicioTest2",null,new ArrayList<>(),null,new HashMap<String,String>(),new ArrayList<>(),new HashMap<String,String>()));
    }
    @Test
    public void getAll() throws SQLException {
        assertEquals(2L,services.getAll().get(1).getId());
    }
    @Test
    public void getAll2() throws SQLException{
        assertEquals(2L,services.getAll().size());
    }

    @Test
    public void getById() throws SQLException{
        assertEquals("EjercicioTest2",services.getById(2L).getTitulo());
    }
    @Test
    public void save() throws SQLException{
        assertEquals("EjercicioTest2",services.save(new Ejercicio("EjercicioTest2",null,new ArrayList<>(),null,new HashMap<String,String>(),new ArrayList<>(),new HashMap<String,String>())).getTitulo());
    }

}
