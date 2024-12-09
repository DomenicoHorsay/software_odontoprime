package org.project.view;

import javax.swing.*;
import java.awt.*;

public class DentistaFormDialog extends JDialog {

    public DentistaFormDialog(JFrame parentFrame, Object dentistaId) {
        super(parentFrame, dentistaId == null ? "Adicionar Dentista" : "Editar Dentista", true);

        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parentFrame);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nome:"));
        JTextField nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Especialidade:"));
        JTextField especialidadeField = new JTextField();
        formPanel.add(especialidadeField);

        formPanel.add(new JLabel("Telefone:"));
        JTextField telefoneField = new JTextField();
        formPanel.add(telefoneField);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> {
            // Lógica para salvar no banco de dados
            JOptionPane.showMessageDialog(this, "Dentista salvo com sucesso!");
            dispose();
        });

        // Ação do botão Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }
}
