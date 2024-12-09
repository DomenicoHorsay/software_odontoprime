package org.project.dao;

import org.project.model.Material;
import org.project.model.MovimentacaoEstoque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    private Connection conexao;

    public EstoqueDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void registrarMovimentacao(MovimentacaoEstoque movimentacao) throws SQLException {
        String sql = "INSERT INTO movimentacoes_estoque (material_id, tipo_movimentacao, quantidade, observacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, movimentacao.getMaterialId());
            stmt.setString(2, movimentacao.getTipoMovimentacao());
            stmt.setInt(3, movimentacao.getQuantidade());
            stmt.setString(4, movimentacao.getObservacao());
            stmt.executeUpdate();
        }

        // Atualizar quantidade no estoque
        atualizarQuantidade(movimentacao.getMaterialId(), movimentacao.getTipoMovimentacao(), movimentacao.getQuantidade());
    }

    private void atualizarQuantidade(int materialId, String tipoMovimentacao, int quantidade) throws SQLException {
        String operacao = tipoMovimentacao.equals("ENTRADA") ? "+" : "-";
        String sql = "UPDATE materiais SET quantidade = quantidade " + operacao + " ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setInt(2, materialId);
            stmt.executeUpdate();
        }
    }

    public List<Material> listarMateriais() throws SQLException {
        List<Material> materiais = new ArrayList<>();
        String sql = "SELECT * FROM materiais";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("id"));
                material.setNome(rs.getString("nome"));
                material.setDescricao(rs.getString("descricao"));
                material.setQuantidade(rs.getInt("quantidade"));
                material.setUnidadeMedida(rs.getString("unidade_medida"));
                material.setValidade(rs.getDate("validade").toLocalDate());
                material.setEstoqueMinimo(rs.getInt("estoque_minimo"));
                material.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
                materiais.add(material);
            }
        }
        return materiais;
    }
}

