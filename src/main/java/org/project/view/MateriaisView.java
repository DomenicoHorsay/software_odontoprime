package org.project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MateriaisView extends JPanel {

    public MateriaisView() {
        setLayout(new BorderLayout());

        // Título do painel de Materiais
        JLabel titulo = new JLabel("Materiais - Gestão", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(79, 79, 79));
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(800, 50));
        add(titulo, BorderLayout.NORTH);

        // Painel com as abas para gerenciamento de materiais
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        tabbedPane.setBackground(new Color(241, 241, 241));

        // Aba para Cadastro de Materiais
        JPanel cadastroPanel = new JPanel(new BorderLayout());

        // Formulário de Cadastro de Materiais (usando GridBagLayout para alinhamento compacto)
        JPanel formularioCadastro = new JPanel(new GridBagLayout());
        formularioCadastro.setBackground(Color.white);
        formularioCadastro.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos de Entrada de Dados (ajustados para menor altura e alinhados com os labels)
        JLabel lblNome = new JLabel("Material:");
        JTextField txtNome = new JTextField();
        ajustarDimensoes(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:");
        JTextField txtDescricao = new JTextField();
        ajustarDimensoes(txtDescricao);

        JLabel lblQuantidade = new JLabel("Qtd. Estoque:");
        JTextField txtQuantidade = new JTextField();
        ajustarDimensoes(txtQuantidade);

        JLabel lblPreco = new JLabel("Preço:");
        JTextField txtPreco = new JTextField();
        ajustarDimensoes(txtPreco);

        // Adicionando os componentes ao formulário com alinhamento
        gbc.gridx = 0;
        gbc.gridy = 0;
        formularioCadastro.add(lblNome, gbc);
        gbc.gridx = 1;
        formularioCadastro.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formularioCadastro.add(lblDescricao, gbc);
        gbc.gridx = 1;
        formularioCadastro.add(txtDescricao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formularioCadastro.add(lblQuantidade, gbc);
        gbc.gridx = 1;
        formularioCadastro.add(txtQuantidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formularioCadastro.add(lblPreco, gbc);
        gbc.gridx = 1;
        formularioCadastro.add(txtPreco, gbc);

        // Botão para Cadastrar Material
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setBackground(new Color(0, 150, 136));
        btnCadastrar.setForeground(Color.white);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorderPainted(false);

        // Adicionando o botão ao formulário
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formularioCadastro.add(btnCadastrar, gbc);

        // Ação do botão de cadastro
        btnCadastrar.addActionListener(e -> {
            if (txtNome.getText().isEmpty() || txtDescricao.getText().isEmpty() ||
                    txtQuantidade.getText().isEmpty() || txtPreco.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Material cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Adicionando o formulário ao painel de cadastro
        cadastroPanel.add(formularioCadastro, BorderLayout.CENTER);

        // Adicionando a aba de Cadastro de Materiais
        tabbedPane.addTab("Cadastro de Materiais", null, cadastroPanel, "Cadastrar novos materiais");

        // Aba de Controle de Estoque
        JPanel controleEstoquePanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Controle de Estoque", null, controleEstoquePanel, "Gerenciar estoque de materiais");

        // Listener para carregar a interface do Controle de Estoque
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == controleEstoquePanel) {
                controleEstoquePanel.removeAll();
                controleEstoquePanel.add(new GerenciarEstoquePanel(), BorderLayout.CENTER);
                controleEstoquePanel.revalidate();
                controleEstoquePanel.repaint();
            }
        });

        // Aba de Relatórios de Consumo
        JPanel relatoriosPanel = new JPanel(new BorderLayout());

        // Tabela de Relatório de Consumo
        String[] columnNames = {"Material", "Quantidade Consumida", "Data", "Tipo de Movimentação"};
        Object[][] data = {
                {"Material A", "50", "01/12/2024", "Entrada"},
                {"Material B", "30", "01/12/2024", "Saída"},
                {"Material C", "70", "02/12/2024", "Entrada"}
        };
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Adicionando a tabela ao painel de relatórios
        relatoriosPanel.add(scrollPane, BorderLayout.CENTER);

        // Adicionando a aba de Relatórios de Consumo
        tabbedPane.addTab("Relatórios de Consumo", null, relatoriosPanel, "Visualizar relatórios de consumo");

        // Adicionando as abas ao painel principal
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Método para ajustar as dimensões dos JTextFields
    private void ajustarDimensoes(JTextField campo) {
        campo.setPreferredSize(new Dimension(150, 20)); // Altura e largura compactas
        campo.setFont(new Font("Arial", Font.PLAIN, 12));
        campo.setBackground(new Color(240, 240, 240));
        campo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    }
}
