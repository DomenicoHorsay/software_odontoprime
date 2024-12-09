package org.project.view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class DashboardView extends JFrame {
    private JPanel painelCentral;

    public DashboardView() throws SQLException {
        // Configurações gerais da janela
        setTitle("OdontoPrime - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Janela em tela cheia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Impede o redimensionamento da janela
        setResizable(false);

        // Definindo cores neutras e elegantes para a interface
        Color corBotao = new Color(79, 79, 79);
        Color corMenuEBotao = new Color(241, 241, 241); // Um cinza muito claro para o menu e botões
        Color corFundoPrincipal = new Color(241, 241, 241); // Um cinza mais escuro para o fundo principal

        // Painel de Menu Horizontal com cor neutra
        JPanel menuTopo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(corFundoPrincipal); // Cor do fundo do menu
            }
        };
        menuTopo.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Alinhamento à esquerda, espaçamento ajustado
        menuTopo.setPreferredSize(new Dimension(getWidth(), 70)); // Altura do menu ajustada

        // Botões do Menu com ícones e texto
        JButton btnProcedimentos = criarBotaoMenu("Procedimentos", "/procedimentosIcon.png", corMenuEBotao, new ProcedimentosView());
        JButton btnEstoque = criarBotaoMenu("Estoque", "/estoqueIcon.png", corMenuEBotao, new EstoqueView()); // Utiliza a nova EstoqueView
        // Adicionando os botões ao menu

        menuTopo.add(btnProcedimentos);
        menuTopo.add(btnEstoque);
        // Painel central (onde o conteúdo muda de acordo com o menu)
        painelCentral = new JPanel();
        painelCentral.setLayout(new BorderLayout());
        painelCentral.setBackground(corFundoPrincipal); // Cor de fundo do painel central

        // Adicionando o painel central a um JScrollPane (para rolagem)
        JScrollPane scrollPane = new JScrollPane(painelCentral);
        add(scrollPane, BorderLayout.CENTER);

        // Adiciona o menu topo à tela
        add(menuTopo, BorderLayout.NORTH);

        // Inicializa o painel com o conteúdo de Estoque
        mostrarPainel(new ProcedimentosView()); // Ajuste para carregar EstoqueView inicialmente
    }

    // Método para criar botões de menu com ícones e texto
    private JButton criarBotaoMenu(String texto, String caminhoIcone, Color corFundo, JPanel painel) {
        JButton botao = new JButton();
        botao.setFont(new Font("Arial", Font.PLAIN, 10)); // Fonte maior para melhor legibilidade
        botao.setForeground(new Color(51, 51, 51)); // Texto em preto (ou a cor que você escolher)
        botao.setBackground(corFundo); // Cor de fundo igual ao fundo dos botões e menu
        botao.setPreferredSize(new Dimension(120, 50)); // Aumentando o tamanho dos botões para textos longos
        botao.setFocusPainted(false);
        botao.setBorderPainted(false); // Remove a borda do botão
        botao.setLayout(new BorderLayout()); // Layout para posicionar o ícone em cima e o texto abaixo

        // Adiciona ícone ao botão
        if (caminhoIcone != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource(caminhoIcone));
            Image img = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH); // Ajuste do tamanho do ícone
            JLabel iconLabel = new JLabel(new ImageIcon(img));
            iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o ícone no botão
            botao.add(iconLabel, BorderLayout.NORTH); // Ícone em cima
        }

        // Texto do botão
        JLabel labelTexto = new JLabel(texto, JLabel.CENTER);
        labelTexto.setFont(new Font("Arial", Font.PLAIN, 12));
        labelTexto.setVerticalAlignment(SwingConstants.TOP); // Alinha o texto ao topo para dar mais espaçamento
        labelTexto.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o texto
        botao.add(labelTexto, BorderLayout.SOUTH); // Texto abaixo

        // Efeito hover (quando passa o mouse)
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(new Color(176, 176, 176)); // Cor ao passar o mouse (um tom mais claro do que o fundo)
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(corFundo); // Cor padrão do fundo
            }
        });

        // Ação para mudar o painel conforme o botão é clicado
        botao.addActionListener(e -> {
            if (botao.getText().equals("Relatório")) {
                mostrarPainel(new JPanel()); // Substitua por RelatorioView quando disponível
            } else {
                mostrarPainel(painel);
            }
        });

        return botao;
    }

    // Método para mostrar o painel conforme a opção clicada
    private void mostrarPainel(JPanel novoPainel) {
        painelCentral.removeAll();
        painelCentral.add(novoPainel, BorderLayout.CENTER);
        painelCentral.revalidate();
        painelCentral.repaint();
    }

    public static void main(String[] args) {
        try {
            DashboardView frame = new DashboardView();
            frame.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
