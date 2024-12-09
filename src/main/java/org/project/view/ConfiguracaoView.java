package org.project.view;

import javax.swing.*;
import java.awt.*;

public class ConfiguracaoView extends JPanel {
    public ConfiguracaoView() {
        setLayout(new GridLayout(2, 1));

        // Painel de Configurações Gerais
        JPanel painelGerais = criarPainel("Configurações Gerais");
        painelGerais.add(new JLabel("Nome do Consultório:"));
        painelGerais.add(new JTextField(20));
        painelGerais.add(new JLabel("Endereço:"));
        painelGerais.add(new JTextField(20));
        painelGerais.add(new JLabel("E-mail para notificações:"));
        painelGerais.add(new JTextField(20));
        add(painelGerais);

        // Painel de Configurações de Segurança
        JPanel painelSeguranca = criarPainel("Configurações de Segurança");
        painelSeguranca.add(new JLabel("Senha Mínima:"));
        painelSeguranca.add(new JTextField(5));
        painelSeguranca.add(new JLabel("Criptografia:"));
        painelSeguranca.add(new JComboBox<>(new String[]{"Desativada", "AES 256", "RSA 2048"}));
        painelSeguranca.add(new JLabel("Backup Automático:"));
        painelSeguranca.add(new JCheckBox());
        add(painelSeguranca);
    }

    private JPanel criarPainel(String titulo) {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createTitledBorder(titulo));
        painel.setLayout(new GridLayout(0, 2, 10, 10));
        return painel;
    }
}
