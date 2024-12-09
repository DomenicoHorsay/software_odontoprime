package org.project.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciarEstoquePanel extends JPanel {

    private JComboBox<String> comboMateriais;
    private JTextField txtQuantidade;
    private JTextArea txtObservacao;
    private JButton btnRegistrarEntrada;
    private JButton btnRegistrarSaida;

    public GerenciarEstoquePanel() {
        // Configurações iniciais do painel
        setLayout(new BorderLayout());
        setBackground(new Color(248, 248, 248));  // Cor de fundo mais clara para o painel


        // Painel central com GridBagLayout para organizar os componentes
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBackground(Color.white);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // ComboBox para Seleção de Material
        JLabel lblMaterial = new JLabel("Material:");
        lblMaterial.setFont(new Font("Arial", Font.PLAIN, 14));
        comboMateriais = new JComboBox<>();
        comboMateriais.setFont(new Font("Arial", Font.PLAIN, 14));
        comboMateriais.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCentral.add(lblMaterial, gbc);
        gbc.gridx = 1;
        painelCentral.add(comboMateriais, gbc);

        // Campo de Quantidade
        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setFont(new Font("Arial", Font.PLAIN, 14));
        txtQuantidade = new JTextField(10);
        txtQuantidade.setFont(new Font("Arial", Font.PLAIN, 14));
        txtQuantidade.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCentral.add(lblQuantidade, gbc);
        gbc.gridx = 1;
        painelCentral.add(txtQuantidade, gbc);

        // Campo de Observação
        JLabel lblObservacao = new JLabel("Observação:");
        lblObservacao.setFont(new Font("Arial", Font.PLAIN, 14));
        txtObservacao = new JTextArea(4, 20);
        txtObservacao.setFont(new Font("Arial", Font.PLAIN, 14));
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);
        txtObservacao.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        JScrollPane scrollObservacao = new JScrollPane(txtObservacao);
        scrollObservacao.setPreferredSize(new Dimension(300, 100));
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCentral.add(lblObservacao, gbc);
        gbc.gridx = 1;
        painelCentral.add(scrollObservacao, gbc);

        // Adicionando painel central
        add(painelCentral, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(248, 248, 248));
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        // Botões Registrar Entrada e Saída
        btnRegistrarEntrada = new JButton("Registrar Entrada");
        btnRegistrarEntrada.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarEntrada.setBackground(new Color(0, 150, 136));
        btnRegistrarEntrada.setForeground(Color.white);
        btnRegistrarEntrada.setPreferredSize(new Dimension(200, 40));
        btnRegistrarEntrada.setFocusPainted(false);
        btnRegistrarEntrada.setBorderPainted(false);

        btnRegistrarSaida = new JButton("Registrar Saída");
        btnRegistrarSaida.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegistrarSaida.setBackground(new Color(255, 87, 34));  // Cor vibrante para saída
        btnRegistrarSaida.setForeground(Color.white);
        btnRegistrarSaida.setPreferredSize(new Dimension(200, 40));
        btnRegistrarSaida.setFocusPainted(false);
        btnRegistrarSaida.setBorderPainted(false);

        painelBotoes.add(btnRegistrarEntrada);
        painelBotoes.add(btnRegistrarSaida);

        // Adicionando o painel de botões na parte inferior
        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos dos botões
        btnRegistrarEntrada.addActionListener(e -> registrarMovimentacao("ENTRADA"));
        btnRegistrarSaida.addActionListener(e -> registrarMovimentacao("SAIDA"));
    }

    private void registrarMovimentacao(String tipo) {
        // Lógica para registrar entrada ou saída no banco usando EstoqueDAO
        // Exemplo de lógica de validação simples:
        String material = (String) comboMateriais.getSelectedItem();
        String quantidade = txtQuantidade.getText();
        String observacao = txtObservacao.getText();

        if (material == null || material.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um material.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quantidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite a quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Realizar a lógica de registro de movimentação (entrada/saída)
        JOptionPane.showMessageDialog(this, tipo + " de material registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
