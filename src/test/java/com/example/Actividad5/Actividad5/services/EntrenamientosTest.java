package com.example.Actividad5.Actividad5.services;

import com.example.Actividad5.Actividad5.conexion.Ram;
import com.example.Actividad5.Actividad5.entities.Ejercicio;
import com.example.Actividad5.Actividad5.entities.Entrenamiento;
import com.example.Actividad5.Actividad5.entities.Jugador;
import com.example.Actividad5.Actividad5.entities.enums.Recuperacion;
import com.example.Actividad5.Actividad5.entities.enums.Resistencia;
import com.example.Actividad5.Actividad5.entities.enums.Velocidad;
import com.example.Actividad5.Actividad5.services.ram.EntrenamientosServicesRAM;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.*;

import static org.testng.AssertJUnit.assertEquals;

@SpringBootTest
public class EntrenamientosTest {

    IEntrenamientosServices<Entrenamiento> services;
    public EntrenamientosTest(){
        services = new EntrenamientosServicesRAM();
    }
    @Before
    public void clean(){
        Ram.getInstance().getEntrenamientos().clear();
        Entrenamiento.reseteaContador();
        HashMap<String, String> dureza = new HashMap<>();
        dureza.put(Recuperacion.class.getName(),Recuperacion.getValor(Recuperacion.HIGH.getNumero()));
        dureza.put(Resistencia.class.getName(),Resistencia.getValor(Resistencia.MEDIUM.getNumero()));
        dureza.put(Velocidad.class.getName(),Velocidad.getValor(Velocidad.LOW.getNumero()));

        HashMap<String, String> dureza2 = new HashMap<>();
        dureza2.put(Recuperacion.class.getName(),Recuperacion.getValor(Recuperacion.HIGH.getNumero()));
        dureza2.put(Resistencia.class.getName(),Resistencia.getValor(Resistencia.MEDIUM.getNumero()));
        dureza2.put(Velocidad.class.getName(),Velocidad.getValor(Velocidad.LOW.getNumero()));

        List<Ejercicio> ejercicios = new ArrayList<>();
        ejercicios.add(new Ejercicio("EjercicioTest","DescripcionTest",null,"10:00",dureza,null,null));
        ejercicios.add(new Ejercicio("EjercicioTest2","DescripcionTest2",null,"20:00",dureza2,null,null));

        List<Jugador> asistentes = new ArrayList<>();
        asistentes.add(new Jugador("123","nombreTest","apellidosTest",null,null,null,null));
        asistentes.add(new Jugador("456","nombreTest","apellidosTest",null,null,null,null));
        asistentes.add(new Jugador("789","nombreTest2","apellidosTest2",null,null,null,null));

        List<Ejercicio> ejercicios2 = new ArrayList<>();
        ejercicios2.add(new Ejercicio("EjercicioTest","DescripcionTest",null,"10:00",dureza,null,null));

        List<Jugador> asistentes2 = new ArrayList<>();
        asistentes2.add(new Jugador("123","nombreTest","apellidosTest",null,null,null,null));

        Ram.getInstance().getEntrenamientos().add(new Entrenamiento(new Date(),ejercicios,asistentes,null));
        Ram.getInstance().getEntrenamientos().add(new Entrenamiento(new Date(),ejercicios2,asistentes2,null));
    }
    @Test
    public void getAll() throws SQLException {
        assertEquals(2,services.getAll().size());
    }
    @Test
    public void getById() throws SQLException{
        assertEquals(new Long(2),(services.getById(1L).getDurezaMedia()));
    }
    @Test
    public void save() throws SQLException{
        List<Ejercicio> ejercicios = new ArrayList<>();
        Entrenamiento nuevo = new Entrenamiento(new Date(),ejercicios,new ArrayList<>(),null);
        Entrenamiento e = services.save(nuevo);
        assertEquals(3L,Ram.getInstance().getEntrenamientos().size());
    }
}
