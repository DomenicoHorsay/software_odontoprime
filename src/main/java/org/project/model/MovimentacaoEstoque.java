package org.project.model;

import java.time.LocalDateTime;

public class MovimentacaoEstoque {
    private int id;
    private int materialId;
    private String tipoMovimentacao; // ENTRADA ou SAIDA
    private int quantidade;
    private LocalDateTime dataMovimentacao;
    private String observacao;

    // Construtor padrão
    public MovimentacaoEstoque() {
    }

    // Construtor com parâmetros
    public MovimentacaoEstoque(int id, int materialId, String tipoMovimentacao, int quantidade, LocalDateTime dataMovimentacao, String observacao) {
        this.id = id;
        this.materialId = materialId;
        this.tipoMovimentacao = tipoMovimentacao;
        this.quantidade = quantidade;
        this.dataMovimentacao = dataMovimentacao;
        this.observacao = observacao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public void setTipoMovimentacao(String tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
