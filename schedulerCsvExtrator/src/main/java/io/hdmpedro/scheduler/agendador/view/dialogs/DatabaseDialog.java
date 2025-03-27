package io.hdmpedro.scheduler.agendador.view.dialogs;

import io.hdmpedro.scheduler.agendador.model.Database;

import javax.swing.*;
import java.awt.*;

public class DatabaseDialog extends JDialog {

    private Database database;
    private boolean saved = false;

    private final JTextField txtName = new JTextField(20);
    private final JTextField txtHost = new JTextField(20);
    private final JTextField txtPort = new JTextField(20);
    private final JTextField txtDatabaseName = new JTextField(20);
    private final JTextField txtUser = new JTextField(20);
    private final JPasswordField txtPassword = new JPasswordField(20);

    public DatabaseDialog(Frame owner) {
        super(owner, "Novo Banco", true);
        initUI();
    }

    public DatabaseDialog(Frame owner, Database existingDb) {
        this(owner);
        populateFields(existingDb);
    }

    private void initUI() {
        setLayout(new GridLayout(0, 2, 5, 5));

        add(new JLabel("Nome:"));
        add(txtName);
        add(new JLabel("Host:"));
        add(txtHost);
        add(new JLabel("Porta:"));
        add(txtPort);
        add(new JLabel("Nome do Banco:"));
        add(txtDatabaseName);
        add(new JLabel("Usuário:"));
        add(txtUser);
        add(new JLabel("Senha:"));
        add(txtPassword);

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(e -> saveDatabase());

        add(btnSave);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void populateFields(Database db) {
        txtName.setText(db.getName());
        txtHost.setText(db.getHost());
        txtPort.setText(db.getPort());
        txtDatabaseName.setText(db.getDatabaseName());
        txtUser.setText(db.getUser());
        txtPassword.setText(db.getPassword());
    }

    private void saveDatabase() {
        database = new Database(
                txtName.getText(),
                txtHost.getText(),
                txtPort.getText(),
                txtDatabaseName.getText(),
                txtUser.getText(),
                new String(txtPassword.getPassword())
        );
        saved = true;
        dispose();
    }

    public boolean showDialog() {
        setVisible(true);
        return saved;
    }

    public Database getDatabase() {
        return database;
    }
}