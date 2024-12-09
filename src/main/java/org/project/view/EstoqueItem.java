package org.project.view;

public class EstoqueItem {
    private String material;
    private int quantidade;
    private double precoUnitario;

    public EstoqueItem(String material, int quantidade, double precoUnitario) {
        this.material = material;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return "EstoqueItem{" +
                "material='" + material + '\'' +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                '}';
    }
}
