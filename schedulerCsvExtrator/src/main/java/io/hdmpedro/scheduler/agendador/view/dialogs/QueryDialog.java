package io.hdmpedro.scheduler.agendador.view.dialogs;


import io.hdmpedro.scheduler.agendador.model.Database;
import io.hdmpedro.scheduler.agendador.model.Query;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QueryDialog extends JDialog {

    private Query query = new Query();
    private boolean saved = false;

    private final JComboBox<String> dbCombo = new JComboBox<>();
    private final JTextArea sqlArea = new JTextArea(5, 30);
    private final JTextField txtOutputPath = new JTextField(20);
    private final JTextField txtFileName = new JTextField(20);
    private final JTextField txtDelimiter = new JTextField(5);

    public QueryDialog(Frame owner, List<Database> databases) {
        super(owner, "Nova Consulta", true);
        initUI(databases);
    }

    public QueryDialog(Frame owner, List<Database> databases, Query existingQuery) {
        this(owner, databases);
        populateFields(existingQuery);
    }

    private void initUI(List<Database> databases) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        databases.forEach(db -> dbCombo.addItem(db.getName()));
        txtDelimiter.setText(",");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Banco de Dados:"), gbc);

        gbc.gridx = 1;
        add(dbCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("SQL:"), gbc);

        gbc.gridx = 1;
        add(new JScrollPane(sqlArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Caminho de Saída:"), gbc);

        gbc.gridx = 1;
        add(txtOutputPath, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Nome do Arquivo:"), gbc);

        gbc.gridx = 1;
        add(txtFileName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Delimitador:"), gbc);

        gbc.gridx = 1;
        add(txtDelimiter, gbc);

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(e -> saveQuery());

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnSave, gbc);

        pack();
        setLocationRelativeTo(getOwner());
    }

    private void populateFields(Query existingQuery) {
        dbCombo.setSelectedItem(existingQuery.getDatabaseRef());
        sqlArea.setText(existingQuery.getSql());
        txtOutputPath.setText(existingQuery.getOutputPath());
        txtFileName.setText(existingQuery.getFileName());
        txtDelimiter.setText(existingQuery.getDelimiter());
    }

    private void saveQuery() {
        query.setDatabaseRef((String) dbCombo.getSelectedItem());
        query.setSql(sqlArea.getText());
        query.setOutputPath(txtOutputPath.getText());
        query.setFileName(txtFileName.getText());
        query.setDelimiter(txtDelimiter.getText());
        saved = true;
        dispose();
    }

    public boolean showDialog() {
        setVisible(true);
        return saved;
    }

    public Query getQuery() {
        return query;
    }
}