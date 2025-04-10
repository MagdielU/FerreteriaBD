/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Modelo.Venta;

import Util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Gena
 */
public class VentaDAO {
    public void crearVenta(Venta venta) throws SQLException {
    String sql = """
        INSERT INTO Ventas (
            id_cliente, 
            id_empleado, 
            fecha_venta, 
            total_venta
        ) VALUES (?, ?, ?, ?)""";
    
    try (Connection c = ConexionDB.getConnection();
         PreparedStatement stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, venta.getIdCliente());
        stmt.setInt(2, venta.getIdEmpleado());
        stmt.setTimestamp(3, new java.sql.Timestamp(venta.getFechaVenta().getTime()));
        stmt.setFloat(4, venta.getTotalVenta());
        stmt.executeUpdate();
    }
}

    public List<Venta> leerTodasVentas() throws SQLException {
        String sql = "SELECT * FROM Ventas";
        List<Venta> ventas = new ArrayList<>();

        try (Connection c = ConexionDB.getConnection();
             PreparedStatement stmt = c.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setIdCliente(rs.getInt("id_cliente"));
                venta.setIdEmpleado(rs.getInt("id_empleado"));
                venta.setFechaVenta(rs.getTimestamp("fecha_venta"));
                venta.setTotalVenta(rs.getFloat("total_venta"));
                ventas.add(venta);
            }
        }
        return ventas;
    }
    // Método para actualizar una venta
public void actualizarVenta(Venta venta) throws SQLException {
    String sql = "UPDATE Ventas SET id_cliente = ?, id_empleado = ?, fecha_venta = ?, total_venta = ? WHERE id_venta = ?";
    
    try (Connection c = ConexionDB.getConnection();
         PreparedStatement stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, venta.getIdCliente());
        stmt.setInt(2, venta.getIdEmpleado());
        stmt.setTimestamp(3, new java.sql.Timestamp(venta.getFechaVenta().getTime()));
        stmt.setFloat(4, venta.getTotalVenta());
        stmt.setInt(5, venta.getIdVenta());
        stmt.executeUpdate();
    }
}

// Método para eliminar una venta
public void eliminarVenta(int idVenta) throws SQLException {
    String sql = "DELETE FROM Ventas WHERE id_venta = ?";
    
    try (Connection c = ConexionDB.getConnection();
         PreparedStatement stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, idVenta);
        stmt.executeUpdate();
    }
}
public static void main(String[] args) {
    try {
        VentaDAO dao = new VentaDAO();
        Venta venta = new Venta();
        venta.setIdVenta(1); // ID existente
        venta.setIdCliente(1);
        venta.setIdEmpleado(2);
        venta.setFechaVenta(new java.util.Date());
        venta.setTotalVenta(500.0f);
        dao.actualizarVenta(venta);
        System.out.println("Venta actualizada.");
    } catch (SQLException e) {
        System.err.println("Error: " + e.getMessage());
    }
}
}
