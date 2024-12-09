package org.project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DentistaPanel extends JPanel {

    public DentistaPanel(JFrame parentFrame) {
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Gerenciamento de Dentistas", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Tabela de Dentistas
        String[] colunas = {"ID", "Nome", "Especialidade", "Telefone"};
        Object[][] dados = {
                // Aqui você pode carregar os dados dos dentistas do banco de dados
        };
        DefaultTableModel tableModel = new DefaultTableModel(dados, colunas);
        JTable tabelaDentistas = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(tabelaDentistas);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);

        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        btnAdicionar.addActionListener(e -> {
            // Abrir diálogo para adicionar dentista
            DentistaFormDialog formDialog = new DentistaFormDialog(parentFrame, null);
            formDialog.setVisible(true);

            // Atualizar tabela após adicionar
            // Aqui você pode recarregar os dados do banco de dados e atualizar o modelo da tabela
        });

        btnEditar.addActionListener(e -> {
            int selectedRow = tabelaDentistas.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(parentFrame, "Selecione um dentista para editar.");
                return;
            }

            // Obter dentista selecionado
            Object dentistaId = tableModel.getValueAt(selectedRow, 0);
            // Carregar dados do dentista e abrir o formulário de edição
            DentistaFormDialog formDialog = new DentistaFormDialog(parentFrame, dentistaId);
            formDialog.setVisible(true);

            // Atualizar tabela após editar
        });

        btnExcluir.addActionListener(e -> {
            int selectedRow = tabelaDentistas.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(parentFrame, "Selecione um dentista para excluir.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Tem certeza que deseja excluir este dentista?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                // Remover dentista do banco de dados
                tableModel.removeRow(selectedRow); // Remover linha da tabela
                JOptionPane.showMessageDialog(parentFrame, "Dentista excluído com sucesso!");
            }
        });
    }
}
