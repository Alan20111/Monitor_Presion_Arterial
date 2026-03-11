package dev.alan20111.monitorpresionarterial.dao.impl;

import dev.alan20111.monitorpresionarterial.config.c_dbConnection;
import dev.alan20111.monitorpresionarterial.dao.i_medicionDAO; // Asegúrate de que tu interfaz se llame así
import dev.alan20111.monitorpresionarterial.models.c_medicion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class c_medicionDAOImpl implements i_medicionDAO {

    @Override
    public boolean insert(c_medicion medicion) {
        String sql = "INSERT INTO mediciones (id_paciente, presion_sistolica, presion_diastolica, pulsos, fecha_hora) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, medicion.getId_paciente());
            pstmt.setInt(2, medicion.getPresion_sistolica());
            pstmt.setInt(3, medicion.getPresion_diastolica());
            pstmt.setInt(4, medicion.getPulsos());
            pstmt.setTimestamp(5, Timestamp.valueOf(medicion.getFecha_hora()));

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error insert medición: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(c_medicion medicion) {
        String sql = "UPDATE mediciones SET id_paciente=?, presion_sistolica=?, presion_diastolica=?, pulsos=?, fecha_hora=? WHERE id_medicion=?";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, medicion.getId_paciente());
            pstmt.setInt(2, medicion.getPresion_sistolica());
            pstmt.setInt(3, medicion.getPresion_diastolica());
            pstmt.setInt(4, medicion.getPulsos());
            pstmt.setTimestamp(5, Timestamp.valueOf(medicion.getFecha_hora()));
            pstmt.setInt(6, medicion.getId_medicion());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error actualizar medición: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id_medicion) {
        String sql = "DELETE FROM mediciones WHERE id_medicion=?";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id_medicion);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminar medición: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<c_medicion> findByPacienteId(int id_paciente) {
        List<c_medicion> mediciones = new ArrayList<>();
        String sql = "SELECT * FROM mediciones WHERE id_paciente=? ORDER BY fecha_hora DESC";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id_paciente);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    mediciones.add(new c_medicion(
                            rs.getInt("id_medicion"),
                            rs.getInt("id_paciente"),
                            rs.getInt("presion_sistolica"),
                            rs.getInt("presion_diastolica"),
                            rs.getInt("pulsos"),
                            rs.getTimestamp("fecha_hora").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error paciente: " + e.getMessage());
        }
        return mediciones;
    }

    @Override
    public List<c_medicion> findAll() {
        List<c_medicion> mediciones = new ArrayList<>();
        String sql = "SELECT * FROM mediciones ORDER BY fecha_hora DESC";
        try (Connection conn = c_dbConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                mediciones.add(new c_medicion(
                        rs.getInt("id_medicion"),
                        rs.getInt("id_paciente"),
                        rs.getInt("presion_sistolica"),
                        rs.getInt("presion_diastolica"),
                        rs.getInt("pulsos"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error mediciones: " + e.getMessage());
        }
        return mediciones;
    }
}