package org.project.dao;

import org.project.model.Medico;
import org.project.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // Método para salvar médico
    public void salvarMedico(Medico medico) throws SQLException {
        String sql = "INSERT INTO medico (nome, crm, especialidade, telefone, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar médico: " + e.getMessage());
            throw e;
        }
    }

    // Método para deletar médico por CRM
    public void delete(String crm) throws SQLException {
        String sql = "DELETE FROM medico WHERE crm = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, crm);
            stmt.executeUpdate();
        }
    }

    // Método para encontrar médico por CRM
    public Medico findByCrm(String crm) throws SQLException {
        String sql = "SELECT * FROM medico WHERE crm = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, crm);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String especialidade = resultSet.getString("especialidade");
                String telefone = resultSet.getString("telefone");
                String email = resultSet.getString("email");

                return new Medico(nome, crm, especialidade, telefone, email);
            }
        }
        return null;
    }

    // Método para encontrar todos os médicos
    public List<Medico> findAll() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                String crm = rs.getString("crm");
                String especialidade = rs.getString("especialidade");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");

                Medico medico = new Medico(nome, crm, especialidade, telefone, email);
                medicos.add(medico);
            }
        }
        return medicos;
    }
}
