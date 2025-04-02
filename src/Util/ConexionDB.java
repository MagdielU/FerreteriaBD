/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

/**
 *
 * @author Gena
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/ferreteria_bd";
    private static final String USER = "root"; // Cambia por tu usuario
    private static final String PASSWORD = "Shallot95"; // Cambla por tu contraseña
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado", e);
        }
        
    }
    public static void main(String[] args) {
        try {
            Connection c = getConnection();
            System.out.println("Conexion exitosa a ferreteria_bd!");
            c.close();
        }catch (SQLException e) {
            System.err.println("Error de conexion: "+ e.getMessage());
        }
    } 
    
}