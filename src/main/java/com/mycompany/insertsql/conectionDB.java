/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.insertsql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class conectionDB{

    private static Connection conexion = null;
    private conectionDB(){}

    public static Connection getConexion() {
        try{
            if(conexion == null || conexion.isClosed()){
                // Verificamos si estamos en Render leyendo una variable de entorno (ej. DB_HOST)
                String hostRender = System.getenv("DB_HOST");

                if (hostRender != null && !hostRender.isEmpty()) {
                    // --- CONFIGURACIÓN PARA POSTGRESQL (RENDER) ---
                    String dbName = System.getenv("DB_NAME");
                    String user = System.getenv("DB_USER");
                    String password = System.getenv("DB_PASSWORD");
                    
                    // URL típica de PostgreSQL en la nube
                    String url = "jdbc:postgresql://" + hostRender + ":5432/" + dbName;
                    
                    Class.forName("org.postgresql.Driver");
                    conexion = DriverManager.getConnection(url, user, password);
                    System.out.println("-> Conexion exitosa a PostgreSQL (Render)");
                    
                } else {
                    // --- CONFIGURACIÓN LOCAL (SQL SERVER) ---
                    String URL_LOCAL = "jdbc:sqlserver://localhost:1433;" 
                            + "databaseName=AvanzadaII;" 
                            + "encrypt=true;" 
                            + "trustServerCertificate=true;";
                    String USER_LOCAL = "sa";
                    String PASSWORD_LOCAL = "Edson1403";

                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    conexion = DriverManager.getConnection(URL_LOCAL, USER_LOCAL, PASSWORD_LOCAL);
                    System.out.println("-> Conexion exitosa a SQL Server (Local)");
                }
            }
        }catch (ClassNotFoundException e){
            System.err.println("Error no se encontro el Driver: " + e.getMessage());
        }catch (SQLException e){
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(){
        try{
            if(conexion != null && !conexion.isClosed()){
                conexion.close();
                System.out.println("-> La conexion fue cerrada");
            }
        }catch(SQLException e){
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }  
}