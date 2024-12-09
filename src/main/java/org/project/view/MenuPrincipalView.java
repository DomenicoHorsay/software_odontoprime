package org.project.view;


import org.project.util.Session;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalView extends JFrame {
    public MenuPrincipalView() {
        if (!Session.isAutenticado()) {
            JOptionPane.showMessageDialog(null, "Você precisa fazer login para acessar essa área.", "Acesso Negado", JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Fecha a aplicação ou redireciona para o login
        }

        setTitle("Menu Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Interface do menu principal
        JLabel label = new JLabel("Bem-vindo, " + Session.getUsuarioLogado().getNome(), SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(label);
    }
}
