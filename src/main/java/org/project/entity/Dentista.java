package org.project.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Dentista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Geração automática da chave primária
    private Long id;

    private String nome;
    private String especialidade;
    private String registroCro;
    private String crm;

    // Getter e Setter para 'id'
    public int getId() {
        return Math.toIntExact(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
    // Getter e Setter para 'nome'
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter para 'especialidade'
    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    // Getter e Setter para 'registroCro'
    public String getRegistroCro() {
        return registroCro;
    }

    public void setRegistroCro(String registroCro) {
        this.registroCro = registroCro;
    }


}
