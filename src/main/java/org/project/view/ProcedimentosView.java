package org.project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProcedimentosView extends JPanel {

    private JTable tabelaHistorico;
    private JTextField campoPaciente;
    private JTextField campoProcedimento;
    private JTextField campoData;

    public ProcedimentosView() {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 241, 241)); // Fundo neutro

        // Cabeçalho
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setBackground(new Color(79, 79, 79));
        painelCabecalho.setPreferredSize(new Dimension(800, 60)); // Tamanho fixo

        JLabel titulo = new JLabel("Gestão de Procedimentos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        painelCabecalho.add(titulo, BorderLayout.CENTER);

        add(painelCabecalho, BorderLayout.NORTH);

        // Painel central
        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 20, 20));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelCentral.setBackground(new Color(241, 241, 241));

        // Seção de histórico
        JPanel painelHistorico = new JPanel(new BorderLayout());
        painelHistorico.setBackground(Color.WHITE);
        painelHistorico.setBorder(BorderFactory.createTitledBorder("Histórico de Procedimentos"));

        String[] colunas = {"ID", "Paciente", "Procedimento", "Data"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaHistorico = new JTable(modeloTabela);
        JScrollPane scrollHistorico = new JScrollPane(tabelaHistorico);

        painelHistorico.add(scrollHistorico, BorderLayout.CENTER);

        // Seção de registro
        JPanel painelRegistro = new JPanel(new GridBagLayout());
        painelRegistro.setBackground(Color.WHITE);
        painelRegistro.setBorder(BorderFactory.createTitledBorder("Registrar Novo Procedimento"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPaciente = new JLabel("Paciente:");
        lblPaciente.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelRegistro.add(lblPaciente, gbc);

        campoPaciente = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelRegistro.add(campoPaciente, gbc);

        JLabel lblProcedimento = new JLabel("Procedimento:");
        lblProcedimento.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        painelRegistro.add(lblProcedimento, gbc);

        campoProcedimento = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelRegistro.add(campoProcedimento, gbc);

        JLabel lblData = new JLabel("Data (yyyy-MM-dd):");
        lblData.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        painelRegistro.add(lblData, gbc);

        campoData = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painelRegistro.add(campoData, gbc);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(0, 150, 136));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setPreferredSize(new Dimension(150, 40));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelRegistro.add(btnRegistrar, gbc);

        painelCentral.add(painelHistorico);
        painelCentral.add(painelRegistro);

        add(painelCentral, BorderLayout.CENTER);

        // Adicionando ação ao botão
        btnRegistrar.addActionListener(registrarProcedimento());

        // Carregar dados iniciais
        atualizarTabelaHistorico();
    }

    private ActionListener registrarProcedimento() {
        return e -> {
            String paciente = campoPaciente.getText().trim();
            String procedimento = campoProcedimento.getText().trim();
            String data = campoData.getText().trim();

            if (paciente.isEmpty() || procedimento.isEmpty() || data.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                java.sql.Date sqlDate = java.sql.Date.valueOf(data);

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO procedimentos (paciente, procedimento, data) VALUES (?, ?, ?)";
                    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                        stmt.setString(1, paciente);
                        stmt.setString(2, procedimento);
                        stmt.setDate(3, sqlDate);

                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Procedimento registrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                        campoPaciente.setText("");
                        campoProcedimento.setText("");
                        campoData.setText("");

                        atualizarTabelaHistorico();
                    }
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Formato de data inválido. Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao registrar procedimento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    private void atualizarTabelaHistorico() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM procedimentos ORDER BY id";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
                    model.setRowCount(0);

                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("paciente"),
                                rs.getString("procedimento"),
                                rs.getDate("data")
                        });
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar histórico: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Procedimentos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new ProcedimentosView());
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/sistema_odontologico"; // Substitua "seu_banco" pelo nome do banco
    private static final String USER = "prime"; // Substitua "seu_usuario" pelo usuário
    private static final String PASSWORD = "prime"; // Substitua "sua_senha" pela senha

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
