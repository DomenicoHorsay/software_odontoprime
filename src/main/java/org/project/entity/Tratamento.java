    package org.project.entity;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "tratamentos_consulta")
    public class Tratamento {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nomeTratamento;
        private Double valor;

        @ManyToOne
            @JoinColumn(name = "consulta_id")
        private Consulta consulta;

        // Getters e setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNomeTratamento() {
            return nomeTratamento;
        }

        public void setNomeTratamento(String nomeTratamento) {
            this.nomeTratamento = nomeTratamento;
        }

        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }

        public Consulta getConsulta() {
            return consulta;
        }

        public void setConsulta(Consulta consulta) {
            this.consulta = consulta;
        }
    }
