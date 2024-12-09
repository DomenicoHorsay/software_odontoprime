package org.project.view;

import org.project.dao.MedicoDAO;
import org.project.model.Medico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MedicoPanel extends JPanel {
    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtCrm = new JTextField(15);
    private final JTextField txtTelefone = new JTextField(15);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtEspecialidade = new JTextField(20);
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final MedicoDAO medicoDAO = new MedicoDAO();

    public MedicoPanel(JFrame frame) {
        setLayout(new BorderLayout());

        // Formulário de cadastro
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Médico"));

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("CRM:"));
        formPanel.add(txtCrm);
        formPanel.add(new JLabel("Telefone:"));
        formPanel.add(txtTelefone);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Especialidade:"));
        formPanel.add(txtEspecialidade);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        formPanel.add(btnSalvar);
        formPanel.add(btnLimpar);

        add(formPanel, BorderLayout.NORTH);

        // Tabela de Médicos
        tableModel = new DefaultTableModel(new String[]{"Nome", "CRM", "Telefone", "Email", "Especialidade"}, 0);
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
        btnSalvar.addActionListener(e -> salvarMedico());
        btnLimpar.addActionListener(e -> limparCampos());
        btnEditar.addActionListener(e -> editarMedico());
        btnExcluir.addActionListener(e -> excluirMedico());

        carregarMedicos();
    }

    private void salvarMedico() {
        // Validação dos campos
        String nome = txtNome.getText().trim();
        String crm = txtCrm.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();
        String especialidade = txtEspecialidade.getText().trim();

        // Validação de campos obrigatórios
        if (nome.isEmpty() || crm.isEmpty() || telefone.isEmpty() || email.isEmpty() || especialidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criação do objeto médico
        org.project.model.Medico medico = new org.project.model.Medico();
        medico.setNome(nome);
        medico.setCrm(crm);
        medico.setTelefone(telefone);
        medico.setEmail(email);
        medico.setEspecialidade(especialidade);

        try {
            // Salva o médico no banco de dados
            medicoDAO.salvarMedico(medico);
            JOptionPane.showMessageDialog(this, "Médico salvo com sucesso!");
            carregarMedicos();  // Atualiza a tabela de médicos
            limparCampos();     // Limpa os campos após salvar
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar médico: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarMedicos() {
        try {
            List<org.project.model.Medico> medicos = medicoDAO.findAll();
            tableModel.setRowCount(0);  // Limpa a tabela antes de adicionar novos dados
            for (org.project.model.Medico medico : medicos) {
                tableModel.addRow(new Object[]{
                        medico.getNome(),
                        medico.getCrm(),
                        medico.getTelefone(),
                        medico.getEmail(),
                        medico.getEspecialidade()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar médicos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarMedico() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um médico para editar.");
            return;
        }

        String crm = (String) tableModel.getValueAt(selectedRow, 1);

        try {
            String nome = txtNome.getText();
            String telefone = txtTelefone.getText();
            String email = txtEmail.getText();
            String especialidade = txtEspecialidade.getText();

            Medico medico = medicoDAO.findByCrm(crm); // Busca médico pelo CRM
            if (medico != null) {
                medico.setNome(nome);
                medico.setTelefone(telefone);
                medico.setEmail(email);
                medico.setEspecialidade(especialidade);

                medicoDAO.salvarMedico(medico); // Atualiza o médico no banco
                JOptionPane.showMessageDialog(this, "Médico atualizado com sucesso!");
                carregarMedicos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Médico não encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao editar médico: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirMedico() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um médico para excluir.");
            return;
        }

        String crm = (String) tableModel.getValueAt(selectedRow, 1);

        try {
            medicoDAO.delete(String.valueOf(Integer.parseInt(crm))); // Exclui o médico pelo CRM
            JOptionPane.showMessageDialog(this, "Médico excluído com sucesso!");
            carregarMedicos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir médico: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCrm.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEspecialidade.setText("");
    }
}
