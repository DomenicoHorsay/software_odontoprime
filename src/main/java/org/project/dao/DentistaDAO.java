package org.project.dao;

import org.project.entity.Dentista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistaDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/seubanco";
    private static final String USER = "usuario";
    private static final String PASSWORD = "senha";

    // Método para consultar todos os dentistas
    public List<Dentista> findAll() {
        List<Dentista> dentistas = new ArrayList<>();
        String sql = "SELECT id, nome, crm FROM dentistas";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Dentista dentista = new Dentista();
                dentista.setId((long) rs.getInt("id"));
                dentista.setNome(rs.getString("nome"));
                dentista.setCrm(rs.getString("crm"));
                dentistas.add(dentista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dentistas;
    }
}
