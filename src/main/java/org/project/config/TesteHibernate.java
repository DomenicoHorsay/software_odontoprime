package org.project.config;

import org.project.config.HibernateUtil;
import org.project.entity.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;

public class TesteHibernate {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Paciente paciente = new Paciente();
        paciente.setNome("Carlos Ferreira");
        paciente.setNascimento(LocalDate.of(1985, 3, 20)); // Corrija se necessário
        paciente.setTelefone("21977777777");
        paciente.setEmail("carlos.ferreira@email.com");
        paciente.setEndereco("Rua C, 123");

        session.save(paciente);
        transaction.commit();
        session.close();

        System.out.println("Paciente salvo com sucesso!");
    }
}
