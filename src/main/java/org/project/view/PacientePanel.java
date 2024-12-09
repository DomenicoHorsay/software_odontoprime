package org.project.view;

import org.project.dao.PacienteDAO;
import org.project.entity.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PacientePanel extends JPanel {
    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtCpf = new JTextField(15);
    private final JTextField txtDataNascimento = new JTextField(10);
    private final JTextField txtTelefone = new JTextField(15);
    private final JTextField txtEndereco = new JTextField(30);
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final PacienteDAO pacienteDAO = new PacienteDAO();

    public PacientePanel(JFrame frame) {
        setLayout(new BorderLayout());

        // Formulário de cadastro
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Paciente"));

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("CPF:"));
        formPanel.add(txtCpf);
        formPanel.add(new JLabel("Data de Nascimento (yyyy-MM-dd):"));
        formPanel.add(txtDataNascimento);
        formPanel.add(new JLabel("Telefone:"));
        formPanel.add(txtTelefone);
        formPanel.add(new JLabel("Endereço:"));
        formPanel.add(txtEndereco);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        formPanel.add(btnSalvar);
        formPanel.add(btnLimpar);

        add(formPanel, BorderLayout.NORTH);

        // Tabela de Pacientes
        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "CPF", "Data de Nascimento", "Telefone", "Endereço"}, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        add(tableScrollPane, BorderLayout.CENTER);

        // Botões de controle
        JPanel btnPanel = new JPanel();
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        btnPanel.add(btnEditar);
        btnPanel.add(btnExcluir);

        add(btnPanel, BorderLayout.SOUTH);

        // Eventos
        btnSalvar.addActionListener(e -> salvarPaciente());
        btnLimpar.addActionListener(e -> limparCampos());
        btnEditar.addActionListener(e -> editarPaciente());
        btnExcluir.addActionListener(e -> excluirPaciente());

        carregarPacientes();
    }

    private void salvarPaciente() {
        try {
            // Obter dados do formulário
            String nome = txtNome.getText();
            String cpf = txtCpf.getText().replaceAll("[^0-9]", ""); // Remove qualquer pontuação do CPF
            LocalDate dataNascimento = LocalDate.parse(txtDataNascimento.getText(), DateTimeFormatter.ISO_DATE); // Formata data para o padrão yyyy-MM-dd
            String telefone = txtTelefone.getText().replaceAll("[^0-9]", ""); // Remove qualquer pontuação do telefone
            String endereco = txtEndereco.getText().trim(); // Remove espaços extras do endereço

            // Validações de campos (exemplo)
            if (cpf.length() != 11) {
                throw new IllegalArgumentException("CPF inválido. O CPF deve ter 11 dígitos.");
            }
            if (telefone.length() != 11) {
                throw new IllegalArgumentException("Telefone inválido. O telefone deve ter 11 dígitos.");
            }

            // Salvar paciente no banco de dados
            pacienteDAO.salvarPaciente(nome, cpf, dataNascimento.toString(), telefone, endereco);

            JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!");
            carregarPacientes();
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar paciente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPaciente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para editar.");
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText().replaceAll("[^0-9]", ""); // Remove qualquer pontuação do CPF
            LocalDate dataNascimento = LocalDate.parse(txtDataNascimento.getText(), DateTimeFormatter.ISO_DATE);
            String telefone = txtTelefone.getText().replaceAll("[^0-9]", ""); // Remove qualquer pontuação do telefone
            String endereco = txtEndereco.getText().trim(); // Remove espaços extras do endereço

            Paciente paciente = pacienteDAO.findById(Math.toIntExact(id)); // Obtém o paciente pelo ID
            if (paciente != null) {
                paciente.setNome(nome);
                paciente.setCpf(cpf);
                paciente.setNascimento(dataNascimento);
                paciente.setTelefone(telefone);
                paciente.setEndereco(endereco);

                pacienteDAO.save(paciente); // Atualiza o paciente no banco
                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
                carregarPacientes();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Paciente não encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar paciente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPaciente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente para excluir.");
            return;
        }

        Long id = (Long) tableModel.getValueAt(selectedRow, 0);

        try {
            pacienteDAO.delete(Math.toIntExact(id)); // Exclui o paciente pelo ID
            JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso!");
            carregarPacientes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir paciente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarPacientes() {
        try {
            List<Paciente> pacientes = pacienteDAO.findAll();
            tableModel.setRowCount(0); // Limpa a tabela
            for (Paciente paciente : pacientes) {
                String formattedCpf = paciente.getCpf().replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4"); // Formata o CPF para exibição
                String formattedTelefone = paciente.getTelefone().replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3"); // Formata o telefone para exibição
                String formattedDataNascimento = paciente.getNascimento().toString(); // Formata a data de nascimento para exibição (já em yyyy-MM-dd)

                tableModel.addRow(new Object[]{
                        paciente.getId(),
                        paciente.getNome(),
                        formattedCpf,
                        formattedDataNascimento,
                        formattedTelefone,
                        paciente.getEndereco()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pacientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtDataNascimento.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
    }
}
