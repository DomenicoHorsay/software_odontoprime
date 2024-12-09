package org.project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueView extends JPanel {
    private JTable tabelaEstoque;
    private DefaultTableModel modeloTabela;
    private List<EstoqueItem> listaEstoque;
    private Connection connection;

    public EstoqueView() {
        setLayout(new BorderLayout());

        // Título da visão do Estoque
        JLabel titulo = new JLabel("Visão Geral do Estoque", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setOpaque(true);
        titulo.setBackground(new Color(79, 79, 79));
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(800, 50));
        add(titulo, BorderLayout.NORTH);

        // Inicializa a lista de estoque
        listaEstoque = new ArrayList<>();

        // Tabela de Estoque
        String[] columnNames = {"Material", "Quantidade", "Preço Unitário", "Total"};
        modeloTabela = new DefaultTableModel(columnNames, 0);
        tabelaEstoque = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaEstoque);
        tabelaEstoque.setFillsViewportHeight(true);
        tabelaEstoque.setDefaultRenderer(Object.class, new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel cell = new JLabel();
                cell.setOpaque(true);

                int quantidade = (int) modeloTabela.getValueAt(row, 1);

                if (quantidade < 10) {
                    cell.setBackground(Color.RED);
                } else {
                    cell.setBackground(Color.GREEN);
                }

                if (isSelected) {
                    cell.setBackground(new Color(173, 216, 230)); // Cor quando selecionado
                }

                cell.setText(value == null ? "" : value.toString());
                return cell;
            }
        });
        tabelaEstoque.setRowHeight(30); // Ajusta a altura das linhas

        // Adicionando a tabela ao painel principal
        add(scrollPane, BorderLayout.CENTER);

        // Botão Adicionar Estoque
        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdicionar.setBackground(new Color(0, 150, 136));
        btnAdicionar.setForeground(Color.white);
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setBorderPainted(false);

        // Ação do botão Adicionar
        btnAdicionar.addActionListener(e -> mostrarDialogoAdicionarEstoque());

        // Botão Editar Estoque
        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEditar.setBackground(new Color(0, 150, 136));
        btnEditar.setForeground(Color.white);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorderPainted(false);

        // Ação do botão Editar
        btnEditar.addActionListener(e -> mostrarDialogoEditarEstoque());

        // Botão Excluir Estoque
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));
        btnExcluir.setBackground(new Color(0, 150, 136));
        btnExcluir.setForeground(Color.white);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setBorderPainted(false);

        // Ação do botão Excluir
        btnExcluir.addActionListener(e -> excluirEstoqueSelecionado());

        // Adicionando botões ao painel inferior
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        // Conexão com o banco de dados PostgreSQL
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sistema_odontologico", "prime", "prime");
            carregarDadosDoBanco();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar um diálogo para adicionar estoque
    private void mostrarDialogoAdicionarEstoque() {
        JDialog dialog = new JDialog((Frame) null, "Adicionar Estoque", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(300, 200); // Tamanho do diálogo
        dialog.setLocationRelativeTo(null); // Centraliza na tela

        // Componentes do diálogo
        JLabel lblMaterial = new JLabel("Material:");
        JTextField txtMaterial = new JTextField(20);
        JLabel lblQuantidade = new JLabel("Quantidade:");
        JTextField txtQuantidade = new JTextField(5);
        JLabel lblPreco = new JLabel("Preço Unitário:");
        JTextField txtPreco = new JTextField(10);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(0, 150, 136));
        btnSalvar.setForeground(Color.white);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorderPainted(false);

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> {
            if (txtMaterial.getText().isEmpty() || txtQuantidade.getText().isEmpty() || txtPreco.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                // Adiciona o novo estoque à lista e atualiza a tabela
                EstoqueItem novoItem = new EstoqueItem(txtMaterial.getText(), Integer.parseInt(txtQuantidade.getText()), Double.parseDouble(txtPreco.getText()));
                listaEstoque.add(novoItem);
                modeloTabela.addRow(new Object[]{novoItem.getMaterial(), novoItem.getQuantidade(), novoItem.getPrecoUnitario(), novoItem.getPrecoUnitario() * novoItem.getQuantidade()});
                salvarEstoqueNoBanco(novoItem);
                atualizarSomaEstoque();
                dialog.dispose(); // Fecha o diálogo
            }
        });

        // Layout para os componentes do diálogo
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(lblMaterial, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        dialog.add(txtMaterial, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(lblQuantidade, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialog.add(txtQuantidade, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(lblPreco, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        dialog.add(txtPreco, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(btnSalvar, gbc);

        dialog.setVisible(true);
    }

    // Método para mostrar um diálogo para editar estoque
    private void mostrarDialogoEditarEstoque() {
        int selectedRow = tabelaEstoque.getSelectedRow();
        if (selectedRow != -1) {
            String material = (String) modeloTabela.getValueAt(selectedRow, 0);
            int quantidade = (int) modeloTabela.getValueAt(selectedRow, 1);
            double precoUnitario = (double) modeloTabela.getValueAt(selectedRow, 2);

            JDialog dialog = new JDialog((Frame) null, "Editar Estoque", true);
            dialog.setLayout(new GridBagLayout());
            dialog.setSize(300, 200); // Tamanho do diálogo
            dialog.setLocationRelativeTo(null); // Centraliza na tela

            // Componentes do diálogo
            JLabel lblMaterial = new JLabel("Material:");
            JTextField txtMaterial = new JTextField(material, 20);
            JLabel lblQuantidade = new JLabel("Quantidade:");
            JTextField txtQuantidade = new JTextField(String.valueOf(quantidade), 5);
            JLabel lblPreco = new JLabel("Preço Unitário:");
            JTextField txtPreco = new JTextField(String.valueOf(precoUnitario), 10);

            JButton btnSalvar = new JButton("Salvar");
            btnSalvar.setBackground(new Color(0, 150, 136));
            btnSalvar.setForeground(Color.white);
            btnSalvar.setFocusPainted(false);
            btnSalvar.setBorderPainted(false);

            // Ação do botão Salvar
            btnSalvar.addActionListener(e -> {
                if (txtMaterial.getText().isEmpty() || txtQuantidade.getText().isEmpty() || txtPreco.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Atualiza o item existente na lista, na tabela e no banco de dados
                    EstoqueItem itemEditado = new EstoqueItem(txtMaterial.getText(), Integer.parseInt(txtQuantidade.getText()), Double.parseDouble(txtPreco.getText()));
                    listaEstoque.set(selectedRow, itemEditado);
                    modeloTabela.setValueAt(itemEditado.getMaterial(), selectedRow, 0);
                    modeloTabela.setValueAt(itemEditado.getQuantidade(), selectedRow, 1);
                    modeloTabela.setValueAt(itemEditado.getPrecoUnitario(), selectedRow, 2);
                    modeloTabela.setValueAt(itemEditado.getPrecoUnitario() * itemEditado.getQuantidade(), selectedRow, 3);
                    atualizarSomaEstoque();
                    atualizarEstoqueNoBanco(itemEditado);
                    dialog.dispose(); // Fecha o diálogo
                }
            });

            // Layout para os componentes do diálogo
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.gridy = 0;
            dialog.add(lblMaterial, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            dialog.add(txtMaterial, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            dialog.add(lblQuantidade, gbc);
            gbc.gridx = 1;
            gbc.gridy = 1;
            dialog.add(txtQuantidade, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            dialog.add(lblPreco, gbc);
            gbc.gridx = 1;
            gbc.gridy = 2;
            dialog.add(txtPreco, gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            dialog.add(btnSalvar, gbc);

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para excluir o estoque selecionado
    private void excluirEstoqueSelecionado() {
        int selectedRow = tabelaEstoque.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este item?", "Excluir Estoque", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Remove o item da lista e da tabela
                EstoqueItem itemParaExcluir = listaEstoque.get(selectedRow);
                listaEstoque.remove(selectedRow);
                modeloTabela.removeRow(selectedRow);
                excluirEstoqueNoBanco(itemParaExcluir);
                atualizarSomaEstoque();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para carregar os dados do banco de dados na tabela
    private void carregarDadosDoBanco() {
        String query = "SELECT * FROM estoque";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String material = resultSet.getString("material");
                int quantidade = resultSet.getInt("quantidade");
                double precoUnitario = resultSet.getDouble("preco_unitario");
                EstoqueItem item = new EstoqueItem(material, quantidade, precoUnitario);
                listaEstoque.add(item);
                modeloTabela.addRow(new Object[]{material, quantidade, precoUnitario, precoUnitario * quantidade});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        atualizarSomaEstoque();
    }

    // Método para atualizar a soma do estoque
    private void atualizarSomaEstoque() {
        double total = listaEstoque.stream().mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidade()).sum();
        System.out.println("Soma do Estoque: " + total);
    }

    // Método para salvar um novo estoque no banco de dados
    private void salvarEstoqueNoBanco(EstoqueItem item) {
        String query = "INSERT INTO estoque (material, quantidade, preco_unitario) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getMaterial());
            preparedStatement.setInt(2, item.getQuantidade());
            preparedStatement.setDouble(3, item.getPrecoUnitario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar um estoque no banco de dados
    private void atualizarEstoqueNoBanco(EstoqueItem item) {
        String query = "UPDATE estoque SET quantidade = ?, preco_unitario = ? WHERE material = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, item.getQuantidade());
            preparedStatement.setDouble(2, item.getPrecoUnitario());
            preparedStatement.setString(3, item.getMaterial());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um estoque no banco de dados
    private void excluirEstoqueNoBanco(EstoqueItem item) {
        String query = "DELETE FROM estoque WHERE material = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getMaterial());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

