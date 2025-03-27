package io.hdmpedro.scheduler.agendador.view.panels;

import io.hdmpedro.scheduler.controller.MainController;

import javax.swing.*;
import java.awt.*;

public class ScheduleConfigPanel extends JPanel {

    private final JComboBox<String> cbType = new JComboBox<>();
    private final JSpinner spInterval = new JSpinner(new SpinnerNumberModel(10, 1, 60, 1));
    private final JTextField txtCron = new JTextField(20);

    public ScheduleConfigPanel(MainController controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        cbType.setModel(new DefaultComboBoxModel<>(new String[]{
                "A cada X segundos",
                "A cada X minutos",
                "Horário fixo diário",
                "Personalizado"
        }));

        spInterval.setPreferredSize(new Dimension(80, 25));
        txtCron.setPreferredSize(new Dimension(250, 25));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Tipo de Agendamento:"), gbc);

        gbc.gridx = 1;
        add(cbType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Intervalo:"), gbc);

        gbc.gridx = 1;
        add(spInterval, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Expressão Cron:"), gbc);

        gbc.gridx = 1;
        add(txtCron, gbc);

        cbType.addActionListener(e -> updateCronExpression());
        spInterval.addChangeListener(e -> updateCronExpression());

        updateCronExpression();
    }

    private void updateCronExpression() {
        String preset = (String) cbType.getSelectedItem();
        int value = (Integer) spInterval.getValue();

        switch (preset) {
            case "A cada X segundos":
                txtCron.setText("0/" + value + " * * * * ?");
                break;
            case "A cada X minutos":
                txtCron.setText("0 0/" + value + " * * * ?");
                break;
            case "Horário fixo diário":
                txtCron.setText("0 0 " + value + " * * ?");
                break;
            default:
                txtCron.setEditable(true);
                break;
        }
    }

    public String getCronExpression() {
        return txtCron.getText();
    }
}