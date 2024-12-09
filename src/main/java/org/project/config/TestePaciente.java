package org.project.config;

import org.project.entity.Paciente;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.config.HibernateUtil;

import java.time.LocalDate;

public class TestePaciente {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Criando um novo paciente
            Paciente paciente = new Paciente();
            paciente.setNome("João da Silva");
            paciente.setEndereco("Rua X, 123");
            paciente.setTelefone("1234-5678");
            paciente.setNascimento(LocalDate.parse("1985-06-15"));

            // Salvando o paciente no banco
            session.save(paciente);
            transaction.commit();

            System.out.println("Paciente salvo com sucesso!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
