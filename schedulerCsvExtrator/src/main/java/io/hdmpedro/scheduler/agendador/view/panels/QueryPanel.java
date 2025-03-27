package io.hdmpedro.scheduler.agendador.view.panels;

import io.hdmpedro.scheduler.controller.MainController;
import io.hdmpedro.scheduler.agendador.model.Query;
import io.hdmpedro.scheduler.agendador.view.dialogs.QueryDialog;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class QueryPanel extends JPanel {

    private final MainController controller;
    private final QueryTableModel tableModel;
    private final JTable table;

    public QueryPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        tableModel = new QueryTableModel(controller);
        table = new JTable(tableModel);

        JToolBar toolBar = new JToolBar();
        JButton btnAdd = new JButton("Adicionar");
        JButton btnEdit = new JButton("Editar");
        JButton btnRemove = new JButton("Remover");

        btnAdd.addActionListener(this::addQuery);
        btnEdit.addActionListener(this::editQuery);
        btnRemove.addActionListener(this::removeQuery);

        toolBar.add(btnAdd);
        toolBar.add(btnEdit);
        toolBar.add(btnRemove);

        add(toolBar, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addQuery(ActionEvent e) {
        QueryDialog dialog = new QueryDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                controller.getConfig().getDatabases()
        );

        if(dialog.showDialog()) {
            controller.addQuery(dialog.getQuery());
            tableModel.fireTableDataChanged();
        }
    }

    private void editQuery(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if(selectedRow >= 0) {
            Query query = controller.getConfig().getQueries().get(selectedRow);
            QueryDialog dialog = new QueryDialog(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    controller.getConfig().getDatabases(),
                    query
            );

            if(dialog.showDialog()) {
                controller.updateQuery(selectedRow, dialog.getQuery());
                tableModel.fireTableRowsUpdated(selectedRow, selectedRow);
            }
        }
    }

    private void removeQuery(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if(selectedRow >= 0) {
            controller.removeQuery(selectedRow);
            tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
        }
    }

    private static class QueryTableModel extends AbstractTableModel {

        private final MainController controller;
        private final String[] columns = {"Banco", "SQL", "Arquivo", "Delimitador"};

        public QueryTableModel(MainController controller) {
            this.controller = controller;
        }

        @Override
        public int getRowCount() {
            return controller.getConfig().getQueries().size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Query query = controller.getConfig().getQueries().get(row);
            switch (col) {
                case 0:
                    return query.getDatabaseRef();
                case 1:
                    return query.getSql();
                case 2:
                    return query.getFileName();
                case 3:
                    return query.getDelimiter();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columns[column];
        }
    }}
