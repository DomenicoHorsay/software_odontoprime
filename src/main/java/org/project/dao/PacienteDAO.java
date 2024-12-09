package org.project.dao;

import org.project.entity.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private Connection connection;

    public PacienteDAO() {
        try {
            // Substitua com os dados corretos do seu banco de dados PostgreSQL
            String url = "jdbc:postgresql://localhost:5432/sistema_odontologico";
            String username = "postgres";
            String password = "1234";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verifica se o paciente com o CPF já existe no banco
    public boolean pacienteExiste(String cpf) throws SQLException {
        String query = "SELECT id FROM pacientes WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se encontrar o paciente
            }
        }
    }

    // Salva um novo paciente no banco de dados
    public void salvarPaciente(String nome, String cpf, String nascimento, String telefone, String endereco) throws SQLException {
        String sql = "INSERT INTO pacientes (nome, cpf, nascimento, telefone, endereco) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);

            // Converte a string de nascimento para o tipo Date (yyyy-MM-dd)
            Date dataNascimento = Date.valueOf(nascimento);
            stmt.setDate(3, dataNascimento); // Passa a data como tipo Date para o banco

            stmt.setString(4, telefone);
            stmt.setString(5, endereco);

            stmt.executeUpdate(); // Executa a inserção no banco de dados
        }
    }


    // Atualiza os dados de um paciente existente no banco
    public void save(Paciente paciente) throws SQLException {
        String sql = "UPDATE pacientes SET nome = ?, cpf = ?, nascimento = ?, telefone = ?, endereco = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getNascimento().toString()); // Data de nascimento no formato yyyy-MM-dd
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEndereco());
            stmt.setLong(6, paciente.getId());
            stmt.executeUpdate(); // Atualiza os dados no banco
        }
    }

    // Exclui um paciente pelo ID
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate(); // Exclui o paciente do banco
        }
    }

    // Busca todos os pacientes no banco de dados
    public List<Paciente> findAll() throws SQLException {
        String sql = "SELECT * FROM pacientes";
        List<Paciente> pacientes = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId((int) rs.getLong("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setNascimento(LocalDate.parse(rs.getString("nascimento")));
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEndereco(rs.getString("endereco"));
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }

    // Busca um paciente pelo ID
    public Paciente findById(int id) throws SQLException {
        String sql = "SELECT * FROM pacientes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente();
                    paciente.setId((int) rs.getLong("id"));
                    paciente.setNome(rs.getString("nome"));
                    paciente.setCpf(rs.getString("cpf"));
                    paciente.setNascimento(LocalDate.parse(rs.getString("nascimento")));
                    paciente.setTelefone(rs.getString("telefone"));
                    paciente.setEndereco(rs.getString("endereco"));
                    return paciente;
                }
            }
        }
        return null; // Retorna null caso o paciente não seja encontrado
    }
}
