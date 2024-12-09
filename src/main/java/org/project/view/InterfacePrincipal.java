package org.project.view;

import javax.swing.*;
import java.awt.*;

public class InterfacePrincipal extends JFrame {

    public InterfacePrincipal() {
        // Configurações básicas da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Painel principal com fundo neutro
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(new Color(241, 241, 241)); // Fundo principal

        // Topo estilizado
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setPreferredSize(new Dimension(800, 60));
        painelTopo.setBackground(new Color(79, 79, 79)); // Cor do topo


    }

}
