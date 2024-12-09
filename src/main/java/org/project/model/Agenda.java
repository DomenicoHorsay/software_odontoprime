package org.project.model;

public class Agenda {

    private String cpf;
    private String paciente;
    private String profissional;
    private String data;
    private String hora;
    private String tratamento;

    public Agenda(String cpf, String paciente, String profissional, String data, String hora, String tratamento) {
        this.cpf = cpf;
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.hora = hora;
        this.tratamento = tratamento;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPaciente() {
        return paciente;
    }

    public String getProfissional() {
        return profissional;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getTratamento() {
        return tratamento;
    }
}
