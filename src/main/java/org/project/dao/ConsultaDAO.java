package org.project.dao;

import org.project.entity.Consulta;
import org.project.entity.Paciente;
import org.project.entity.Dentista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/seubanco";
    private static final String USER = "usuario";
    private static final String PASSWORD = "senha";

    // Método para salvar a consulta
    public void save(Consulta consulta) {
        String sql = "INSERT INTO consultas (paciente_id, dentista_id, data_consulta) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, consulta.getPaciente().getId()); // Assume que Paciente tem um método getId()
            pstmt.setInt(2, consulta.getDentista().getId()); // Assume que Dentista tem um método getId()
            pstmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataConsulta()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para consultar todas as consultas
    public List<Consulta> findAll() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consultas";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Consulta consulta = new Consulta();
                // Aqui você deve preencher os dados da consulta, incluindo paciente e dentista
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultas;
    }
}
