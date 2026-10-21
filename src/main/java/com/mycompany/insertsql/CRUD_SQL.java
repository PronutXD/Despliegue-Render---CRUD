/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.insertsql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author User
 */
public class CRUD_SQL {

    public static void main(String[] args) throws SQLException {
        System.out.println("=== Ejecutando operaciones en tabla Alumnos ===");
        
        //obtner conection
        Connection cn = conectionDB.getConexion();
        if(cn == null){
            System.out.println("No se pudo establecer la conexion");
            return;
        }
        
        //Declarar los objetos de SQL para nuestra base
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar = null;
        PreparedStatement psEliminar = null;
        ResultSet rs = null;
        
        try{
            System.out.println("\n1. Intentando Insertar nuevo Alumno...");
            String psInsert = "INSERT INTO Alumnos (nombre, correo) VALUES (?, ?)";
            
            psInsertar = cn.prepareStatement(psInsert);
            
            //asignaremso valores a nuestros parametros
            psInsertar.setString(1, "Messi Ronaldo");
            psInsertar.setString(2, "messi.ronaldo@uth.hn");
            
            //ejecutar insercion
            int filasAfectadas = psInsertar.executeUpdate();
            if(filasAfectadas > 0){
                System.out.println("¡Alumno Insertado con éxito!");
            }
            
            //Parte B Seleccionar y Visualizar datos en pantalla
            System.out.println("\n2. Consulta de lista de Alumnos en la Base de Datos");
            String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
            psSeleccionar = cn.prepareStatement(sqlSelect);
            rs = psSeleccionar.executeQuery(); 
            
            System.out.println("\n=================================================");
            System.out.println("ID  | Nombre                | Correo        ");
            System.out.println("=================================================");
            
            while(rs.next()){
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                
                //Imprimios en tabla virtual
                System.out.printf("%-3d | %-20s | %s\n", id, nombre, correo);
            }
            
            System.out.println("===================================================");
            
            //Actualizar los datos de la tabla
            System.out.println("\n 3. Actualizar dato de de tabla");
            String sqlUpdate = "UPDATE Alumnos SET nombre = 'Maradona Pele', correo = 'pele@yahoo.com' WHERE id = 9";
            
            psActualizar = cn.prepareStatement(sqlUpdate);
            
            //ejecutar actulizacion
            int filasActualizadas = psActualizar.executeUpdate();
            if(filasActualizadas > 0){
                System.out.println("!Actualizacion con Exito!");
            }
            
            //Eliminar datos de la tabla
            System.out.println("\n 4. Eliminar datos de la tabla");
            String sqlDelete = "DELETE FROM Alumnos WHERE id = 14";
            
            psEliminar = cn.prepareStatement(sqlDelete);
            
            //ejecutar eliminacion
            int filasEliminadas = psEliminar.executeUpdate();
            if(filasEliminadas > 0){
                System.out.println("!Datos Eliminados con Exito!");
            }
            
        }catch(SQLException e){
            System.out.println("Ocurrio un error con la base de datos: " + e.getMessage());
            
        }finally{
            //cerrar la conexion de manera obligatoria
            if(rs != null){
                rs.close();
            }
            if(psInsertar != null){
                psInsertar.close();
            }
            if(psSeleccionar != null){
                psSeleccionar.close();
            }
            
            if(psActualizar != null){
                psActualizar.close();
            }
            
            if(psEliminar != null){
                psEliminar.close();
            }
            
            conectionDB.cerrarConexion();
        }
    }    
}