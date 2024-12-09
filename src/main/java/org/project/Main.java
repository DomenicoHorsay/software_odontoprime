package org.project;

import com.formdev.flatlaf.FlatLightLaf; // Importa o tema do FlatLaf
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.project.view.LoginView;
import org.project.dao.MedicoDAO;
import org.project.model.Medico;

public class Main {
    public static void main(String[] args) {
        // Configura o tema antes de iniciar a aplicação
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Tema claro
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace(); // Mostra o erro se o tema não puder ser aplicado
        }

        // Exibe a tela de login como ponto de entrada principal
        LoginView loginView = new LoginView();
        loginView.setVisible(true);

        // Criação de um objeto Medico para testar a persistência
        Medico medico = new Medico("Dr. João Silva", "123456", "Cardiologia", "1234-5678", "joao.silva@example.com");

        // Tentando salvar o médico no banco de dados
        try {
            MedicoDAO medicoDAO = new MedicoDAO();
            medicoDAO.salvarMedico(medico);  // Salva o médico
            System.out.println("Médico salvo com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao salvar médico: " + e.getMessage());
        }
    }
}
