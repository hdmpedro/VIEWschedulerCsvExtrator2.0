package io.hdmpedro.scheduler.agendador.view;

import io.hdmpedro.scheduler.agendador.view.panels.DatabasePanel;
import io.hdmpedro.scheduler.agendador.view.panels.QueryPanel;
import io.hdmpedro.scheduler.agendador.view.panels.ScheduleConfigPanel;
import io.hdmpedro.scheduler.controller.MainController;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final MainController mainController;

    public MainFrame() {
        mainController = new MainController();
        initUI();
    }

    private void initUI() {
        setTitle("Agendador de Extrações CSV");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Bancos", new DatabasePanel(mainController));
        tabbedPane.addTab("Agendamento", new ScheduleConfigPanel(mainController));
        tabbedPane.addTab("Consultas", new QueryPanel(mainController));

        JButton btnGenerate = new JButton("Gerar XML");
        btnGenerate.addActionListener(e -> {
            mainController.saveConfiguration();
            JOptionPane.showMessageDialog(this, "Configuração salva em config.xml");
        });


        add(tabbedPane, BorderLayout.CENTER);
        add(btnGenerate, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}