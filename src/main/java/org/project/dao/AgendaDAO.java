package org.project.dao;

import org.project.model.Agenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {
    private Connection connection;

    public AgendaDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Agenda agenda) throws SQLException {
        String sql = "INSERT INTO agenda (cpf, paciente, profissional, data, hora, tratamento) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, agenda.getCpf());
            statement.setString(2, agenda.getPaciente());
            statement.setString(3, agenda.getProfissional());
            statement.setDate(4, java.sql.Date.valueOf(agenda.getData())); // Converte string para java.sql.Date
            statement.setTime(5, java.sql.Time.valueOf(agenda.getHora())); // Converte string para java.sql.Time
            statement.setString(6, agenda.getTratamento());

            statement.executeUpdate();
        }
    }

    public void update(Agenda agenda) throws SQLException {
        String sql = "UPDATE agendamentos SET paciente = ?, profissional = ?, data = ?, hora = ?, tratamento = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, agenda.getPaciente());
            stmt.setString(2, agenda.getProfissional());
            stmt.setString(3, agenda.getData());
            stmt.setString(4, agenda.getHora());
            stmt.setString(5, agenda.getTratamento());
            stmt.setString(6, agenda.getCpf());
            stmt.executeUpdate();
        }
    }



    public void delete(String cpf) throws SQLException {
        String sql = "DELETE FROM agenda WHERE cpf = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cpf);
            statement.executeUpdate();
        }
    }


    public List<Agenda> getAllAgendamentos() {
        List<Agenda> agendamentos = new ArrayList<>();
        String query = "SELECT * FROM agenda";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String cpf = rs.getString("cpf");
                String paciente = rs.getString("paciente");
                String profissional = rs.getString("profissional");
                String data = rs.getString("data");
                String hora = rs.getString("hora");
                String tratamento = rs.getString("tratamento");

                Agenda agenda = new Agenda(cpf, paciente, profissional, data, hora, tratamento);
                agendamentos.add(agenda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agendamentos;
    }
}
