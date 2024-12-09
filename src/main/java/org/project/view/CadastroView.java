package org.project.view;

import org.project.dao.MedicoDAO;
import org.project.dao.PacienteDAO;
import org.project.model.Medico;
import org.project.util.ConnectionFactory;


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class CadastroView extends JPanel {

    // Campos para Pacientes
    private JTextField campoNome;
    private JTextField campoCpf;
    private JTextField campoNascimento;
    private JTextField campoTelefone;
    private JTextField campoEndereco;

    // Campos para Médicos
    private JTextField nomeMedicoField;
    private JTextField crmField;
    private JTextField especialidadeField;
    private JTextField telefoneMedicoField;
    private JTextField emailField;

    public CadastroView() {
        // Configuração do layout principal
        setLayout(new BorderLayout());
        setBackground(new Color(241, 241, 241)); // Cor de fundo neutra

        // Título
        JLabel titulo = new JLabel("Cadastro - Consultório", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(79, 79, 79));
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(800, 50));
        add(titulo, BorderLayout.NORTH);

        // Painel com abas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        tabbedPane.setBackground(new Color(241, 241, 241));

        // Aba de Cadastro de Pacientes
        JPanel pacientePanel = criarPainelCadastroPaciente();
        tabbedPane.addTab("Pacientes", null, pacientePanel, "Cadastro de Pacientes");

        // Aba de Cadastro de Médicos
        JPanel medicoPanel = criarPainelCadastroMedico();
        tabbedPane.addTab("Médicos", null, medicoPanel, "Cadastro de Médicos");

        // Adicionando o painel de abas ao centro
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel criarPainelCadastroPaciente() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(241, 241, 241)); // Fundo neutro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos do formulário
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome do Paciente:"), gbc);

        gbc.gridx = 1;
        campoNome = new JTextField(20);
        panel.add(campoNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        campoCpf = new JTextField(20);
        panel.add(campoCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Data de Nascimento:"), gbc);

        gbc.gridx = 1;
        campoNascimento = new JTextField(20);
        panel.add(campoNascimento, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        campoTelefone = new JTextField(20);
        panel.add(campoTelefone, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Endereço:"), gbc);

        gbc.gridx = 1;
        campoEndereco = new JTextField(20);
        panel.add(campoEndereco, gbc);

        // Botão de salvar
        gbc.gridx = 1; gbc.gridy = 5;
        JButton salvarButton = new JButton("Salvar Paciente");
        salvarButton.setBackground(new Color(0, 150, 136));
        salvarButton.setForeground(Color.WHITE);
        salvarButton.setFocusPainted(false);
        salvarButton.addActionListener(e -> validarECadastrarPaciente());
        panel.add(salvarButton, gbc);

        return panel;
    }

    private JPanel criarPainelCadastroMedico() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(241, 241, 241)); // Fundo neutro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos do formulário
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome do Médico:"), gbc);

        gbc.gridx = 1;
        nomeMedicoField = new JTextField(20);
        panel.add(nomeMedicoField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("CRM:"), gbc);

        gbc.gridx = 1;
        crmField = new JTextField(20);
        panel.add(crmField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Especialidade:"), gbc);

        gbc.gridx = 1;
        especialidadeField = new JTextField(20);
        panel.add(especialidadeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        telefoneMedicoField = new JTextField(20);
        panel.add(telefoneMedicoField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("E-mail:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Botão de salvar
        gbc.gridx = 1; gbc.gridy = 5;
        JButton salvarButton = new JButton("Salvar Médico");
        salvarButton.setBackground(new Color(0, 150, 136));
        salvarButton.setForeground(Color.WHITE);
        salvarButton.setFocusPainted(false);
        salvarButton.addActionListener(e -> validarECadastrarMedico());
        panel.add(salvarButton, gbc);

        return panel;
    }

    private void validarECadastrarPaciente() {
        String nome = campoNome.getText();
        String cpf = campoCpf.getText();
        String nascimento = campoNascimento.getText();
        String telefone = campoTelefone.getText();
        String endereco = campoEndereco.getText();

        // Verificar se os campos obrigatórios estão preenchidos
        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar Data de Nascimento (formato: yyyy-MM-dd)
        if (!nascimento.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Data de Nascimento inválida. Use o formato yyyy-MM-dd.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar Telefone (formato: (XX) XXXXX-XXXX)
        if (!telefone.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Telefone inválido. Use o formato (XX) XXXXX-XXXX.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar Endereço (não pode ser vazio)
        if (endereco.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Endereço é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacienteDAO.salvarPaciente(nome, cpf, nascimento, telefone, endereco);
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void validarECadastrarMedico() {
        String nome = nomeMedicoField.getText();
        String crm = crmField.getText();
        String especialidade = especialidadeField.getText();
        String telefone = telefoneMedicoField.getText();
        String email = emailField.getText();

        // Verificar se os campos obrigatórios estão preenchidos
        if (nome.isEmpty() || crm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CRM são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar Telefone (formato: (XX) XXXXX-XXXX)
        if (!telefone.matches("\\(\\d{2}\\) \\d{5}-\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Telefone inválido. Use o formato (XX) XXXXX-XXXX.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar E-mail (verificação básica de formato)
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            JOptionPane.showMessageDialog(this, "E-mail inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = ConnectionFactory.getConnection()) {
            MedicoDAO medicoDAO = new MedicoDAO();
            medicoDAO.salvarMedico(new Medico(nome, crm, especialidade, telefone, email));
            JOptionPane.showMessageDialog(this, "Médico cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar médico: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


}
