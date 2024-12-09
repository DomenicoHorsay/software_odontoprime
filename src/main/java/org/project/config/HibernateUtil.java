package org.project.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.project.entity.Consulta;
import org.project.entity.Dentista;
import org.project.entity.Paciente;
import org.project.entity.Tratamento;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Cria a SessionFactory a partir do arquivo hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Paciente.class)
                    .addAnnotatedClass(Dentista.class)
                    .addAnnotatedClass(Consulta.class)
                    .addAnnotatedClass(Tratamento.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Falha ao inicializar o Hibernate: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
