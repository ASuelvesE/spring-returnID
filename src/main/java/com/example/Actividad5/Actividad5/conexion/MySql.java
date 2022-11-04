package com.example.Actividad5.Actividad5.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySql {

    private static Connection conexion;
    public static Connection getInstance() {

        if(conexion == null){
            try{
                String url = "jdbc:mysql://deportes.cjhojoukydxs.us-east-1.rds.amazonaws.com/appdeportes";
                conexion = DriverManager.getConnection(url,"admin","SaltoRoldan");
                return conexion;
            }catch (SQLException e){
                System.err.println(e.getMessage());
            }
        }
        return conexion;
    }
}
