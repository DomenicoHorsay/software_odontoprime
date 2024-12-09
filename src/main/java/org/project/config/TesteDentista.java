package org.project.config;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.project.entity.Dentista;


public class TesteDentista {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Criando um novo dentista
            Dentista dentista = new Dentista();
            dentista.setNome("Dr. Carlos Oliveira");
            dentista.setEspecialidade("Ortodontia");
            dentista.setRegistroCro("123456");

            // Salvando o dentista no banco
            session.save(dentista);
            transaction.commit();

            System.out.println("Dentista salvo com sucesso!");

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
