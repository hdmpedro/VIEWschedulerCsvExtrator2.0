package io.hdmpedro.scheduler.agendador.view.panels;

import io.hdmpedro.scheduler.controller.MainController;
import io.hdmpedro.scheduler.agendador.model.Database;
import io.hdmpedro.scheduler.agendador.view.dialogs.DatabaseDialog;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DatabasePanel extends JPanel {

    private final JTable table;
    private final DatabaseTableModel model;
    private final MainController controller;

    public DatabasePanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        model = new DatabaseTableModel(controller);
        table = new JTable(model);

        JToolBar toolBar = new JToolBar();
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnRemove = new JButton("Remover");

        btnAdd.addActionListener(this::addDatabase);
        btnEdit.addActionListener(this::editDatabase);
        btnRemove.addActionListener(this::removeDatabase);

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(btnRemove);

        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addDatabase(ActionEvent e) {
        DatabaseDialog dialog = new DatabaseDialog((Frame) SwingUtilities.getWindowAncestor(this));
        if(dialog.showDialog()) {
            controller.addDatabase(dialog.getDatabase());
            model.fireTableDataChanged();
        }
    }

    private void editDatabase(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if(selectedRow >= 0) {
            Database db = controller.getConfig().getDatabases().get(selectedRow);
            DatabaseDialog dialog = new DatabaseDialog(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    db
            );
            if(dialog.showDialog()) {
                controller.updateDatabase(selectedRow, dialog.getDatabase());
                model.fireTableDataChanged();
            }
        }
    }

    private void removeDatabase(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if(selectedRow >= 0) {
            controller.removeDatabase(selectedRow);
            model.fireTableRowsDeleted(selectedRow, selectedRow);
        }
    }

    private class DatabaseTableModel extends AbstractTableModel {

        private final List<Database> databases;
        private final String[] columns = {"Nome", "Host", "Porta", "Banco"};

        public DatabaseTableModel(MainController controller) {
            this.databases = controller.getDatabases();
        }

        @Override
        public int getRowCount() {
            return databases.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Database db = controller.getConfig().getDatabases().get(row);
            switch (col) {
                case 0:
                    return db.getName();
                case 1:
                    return db.getHost();
                case 2:
                    return db.getPort();
                case 3:
                    return db.getDatabaseName();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }
    }
}
