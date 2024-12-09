package org.project.view;

import org.project.dao.ConsultaDAO;
import org.project.dao.DentistaDAO;
import org.project.dao.PacienteDAO;
import org.project.entity.Consulta;
import org.project.entity.Paciente;
import org.project.entity.Dentista;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ConsultaPanel extends JPanel {
    private JTextField txtDataConsulta;
    private JComboBox<Paciente> comboPaciente;
    private JComboBox<Dentista> comboDentista;
    private ConsultaDAO consultaDAO;

    public ConsultaPanel(JFrame parent) throws SQLException {
        consultaDAO = new ConsultaDAO();

        setLayout(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(4, 2));

        formPanel.add(new JLabel("Paciente:"));
        comboPaciente = new JComboBox<>();
        // Preencher comboPaciente com os pacientes do banco
        formPanel.add(comboPaciente);

        formPanel.add(new JLabel("Dentista:"));
        comboDentista = new JComboBox<>();
        // Preencher comboDentista com os dentistas do banco
        formPanel.add(comboDentista);

        formPanel.add(new JLabel("Data da Consulta (yyyy-MM-dd HH:mm):"));
        txtDataConsulta = new JTextField();
        formPanel.add(txtDataConsulta);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarConsulta());
        formPanel.add(btnSalvar);

        add(formPanel, BorderLayout.CENTER);

        // Carregar os dados do banco
        carregarDados();
    }

    private void carregarDados() throws SQLException {
        PacienteDAO pacienteDAO = new PacienteDAO();
        List<Paciente> pacientes = pacienteDAO.findAll();
        DentistaDAO dentistaDAO = new DentistaDAO();
        List<Dentista> dentistas = dentistaDAO.findAll();

        for (Paciente paciente : pacientes) {
            comboPaciente.addItem(paciente);
        }

        for (Dentista dentista : dentistas) {
            comboDentista.addItem(dentista);
        }
    }

    private void salvarConsulta() {
        String dataTexto = txtDataConsulta.getText();
        LocalDateTime dataConsulta = null;

        try {
            dataConsulta = LocalDateTime.parse(dataTexto, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido. Use o formato yyyy-MM-dd HH:mm.");
            return;
        }

        // Verificar se paciente e dentista foram selecionados
        Paciente pacienteSelecionado = (Paciente) comboPaciente.getSelectedItem();
        Dentista dentistaSelecionado = (Dentista) comboDentista.getSelectedItem();

        if (pacienteSelecionado == null || dentistaSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione o paciente e o dentista.");
            return;
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(pacienteSelecionado);
        consulta.setDentista(dentistaSelecionado);
        consulta.setDataConsulta(dataConsulta);

        consultaDAO.save(consulta);

        JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
    }
}
