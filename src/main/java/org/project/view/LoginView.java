package org.project.view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.*;

public class LoginView extends JFrame {
    private JTextField usuarioLoginField;
    private JPasswordField senhaLoginField;

    public LoginView() {
        // Configura o tema FlatLaf para um visual moderno
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setUndecorated(true); // Remove a borda da janela
        setTitle("OdontoPrime - Login");
        setSize(400, 460); // Tamanho da janela
        setResizable(false); // Impede o redimensionamento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        JPanel painelLogin = new JPanel();
        painelLogin.setLayout(new BorderLayout());
        painelLogin.setBackground(new Color(0, 41, 68));

        // Painel de controle no topo (título e botão de fechar)
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BorderLayout());
        painelTopo.setBackground(new Color(0, 41, 68));

        // Botão de fechar com texto "X" e borda redonda branca transparente
        JButton btnFechar = new JButton("X");
        btnFechar.setFont(new Font("Sanserif", Font.BOLD, 20));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setFocusPainted(false);


        // Ação do botão para fechar a aplicação
        btnFechar.addActionListener(e -> System.exit(0));

        // Adicionar o botão no canto superior direito
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(new Color(0, 41, 68));
        painelBotoes.add(btnFechar);

        painelTopo.add(painelBotoes, BorderLayout.NORTH);
        painelLogin.add(painelTopo, BorderLayout.NORTH);

        // Painel com a logo no centro
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(0, 41, 68));
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(getClass().getResource("/logo.png"))); // Caminho da logo
        logoPanel.add(logoLabel);

        painelLogin.add(logoPanel, BorderLayout.CENTER);

        // Painel com os campos de login
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        painelFormulario.setBackground(new Color(0, 41, 68));

        // Campo de usuário
        usuarioLoginField = new JTextField();
        usuarioLoginField.setPreferredSize(new Dimension(200, 35));
        TitledBorder usuarioBorder = BorderFactory.createTitledBorder("Usuário");
        usuarioBorder.setTitleColor(Color.WHITE);
        usuarioLoginField.setBorder(usuarioBorder);
        usuarioLoginField.setFont(new Font("Arial", Font.PLAIN, 13));
        usuarioLoginField.setBackground(new Color(0, 41, 68));
        usuarioLoginField.setForeground(Color.white);
        usuarioLoginField.setCaretColor(Color.WHITE);

        // Campo de senha
        senhaLoginField = new JPasswordField();
        senhaLoginField.setPreferredSize(new Dimension(200, 35));
        TitledBorder senhaBorder = BorderFactory.createTitledBorder("Senha");
        senhaBorder.setTitleColor(Color.WHITE);
        senhaLoginField.setBorder(senhaBorder);
        senhaLoginField.setFont(new Font("Arial", Font.PLAIN, 13));
        senhaLoginField.setBackground(new Color(0, 41, 68));
        senhaLoginField.setForeground(Color.white);
        senhaLoginField.setCaretColor(Color.WHITE);

        // Botão de login
        JButton loginButton = new JButton("Entrar");
        loginButton.setBackground(new Color(0, 41, 68));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(150, 30));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> realizarLogin());

        painelFormulario.add(usuarioLoginField);
        painelFormulario.add(Box.createVerticalStrut(10));
        painelFormulario.add(senhaLoginField);
        painelFormulario.add(Box.createVerticalStrut(20));
        painelFormulario.add(loginButton);

        painelLogin.add(painelFormulario, BorderLayout.SOUTH);

        add(painelLogin);
    }

    private void realizarLogin() {
        String usuario = usuarioLoginField.getText();
        String senha = new String(senhaLoginField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sistema_odontologico", "postgres", "1234")) {
            String sql = "SELECT senha FROM usuarios WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaArmazenada = rs.getString("senha");
                if (senha.equals(senhaArmazenada)) {
                    JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Fecha a janela de login
                    new DashboardView().setVisible(true); // Abre a DashboardView
                } else {
                    JOptionPane.showMessageDialog(this, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }

    // Classe personalizada para criar uma borda redonda
    class RoundBorder implements Border {
        private int radius;
        private Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
