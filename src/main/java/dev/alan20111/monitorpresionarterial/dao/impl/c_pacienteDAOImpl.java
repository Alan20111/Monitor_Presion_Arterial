package dev.alan20111.monitorpresionarterial.dao.impl;

import dev.alan20111.monitorpresionarterial.config.c_dbConnection;
import dev.alan20111.monitorpresionarterial.dao.i_pacienteDAO; // Asegúrate de que tu interfaz se llame así
import dev.alan20111.monitorpresionarterial.models.c_paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class c_pacienteDAOImpl implements i_pacienteDAO {

    @Override
    public boolean insert(c_paciente paciente) {
        String sql = "INSERT INTO pacientes (nombres, apellidos, fecha_nacimiento, genero, peso, altura) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paciente.getNombres());
            pstmt.setString(2, paciente.getApellidos());
            pstmt.setDate(3, Date.valueOf(paciente.getFecha_nacimiento()));
            pstmt.setString(4, paciente.getGenero());
            pstmt.setDouble(5, paciente.getPeso());
            pstmt.setDouble(6, paciente.getAltura());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert paciente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(c_paciente paciente) {
        String sql = "UPDATE pacientes SET nombres=?, apellidos=?, fecha_nacimiento=?, genero=?, peso=?, altura=? WHERE id_paciente=?";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, paciente.getNombres());
            pstmt.setString(2, paciente.getApellidos());
            pstmt.setDate(3, Date.valueOf(paciente.getFecha_nacimiento()));
            pstmt.setString(4, paciente.getGenero());
            pstmt.setDouble(5, paciente.getPeso());
            pstmt.setDouble(6, paciente.getAltura());
            pstmt.setInt(7, paciente.getId_paciente());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar paciente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id_paciente) {
        String sql = "DELETE FROM pacientes WHERE id_paciente=?";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id_paciente);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar paciente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<c_paciente> findById(int id_paciente) {
        String sql = "SELECT * FROM pacientes WHERE id_paciente=?";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id_paciente);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    c_paciente paciente = new c_paciente(
                            rs.getInt("id_paciente"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("genero"),
                            rs.getDouble("peso"),
                            rs.getDouble("altura")
                    );
                    return Optional.of(paciente);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<c_paciente> findAll() {
        List<c_paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pacientes.add(new c_paciente(
                        rs.getInt("id_paciente"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("genero"),
                        rs.getDouble("peso"),
                        rs.getDouble("altura")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error pacientes: " + e.getMessage());
        }
        return pacientes;
    }
}