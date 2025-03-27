package io.hdmpedro.scheduler.controller;

import io.hdmpedro.scheduler.agendador.model.Database;
import io.hdmpedro.scheduler.agendador.model.Query;
import io.hdmpedro.scheduler.agendador.model.SchedulerConfig;

import javax.swing.*;
import java.util.List;

public class MainController {

    private static final String CONFIG_FILE = "config.xml";
    private SchedulerConfig config = new SchedulerConfig();
    private XMLConfigController xmlConfigController = new XMLConfigController();

    public MainController() {
        xmlConfigController = new XMLConfigController();
        try {
            config = xmlConfigController.loadConfig(CONFIG_FILE);
        } catch (Exception e) {
            config = new SchedulerConfig();
            JOptionPane.showMessageDialog(null,
                    "Configuração anterior não encontrada. Criando nova configuração.",
                    "Informação",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public void addDatabase(Database database) {
        config.getDatabases().add(database);
        saveConfiguration();
    }

    public void saveConfiguration() {
        try {
            xmlConfigController.saveConfig(config, CONFIG_FILE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao salvar configuração: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }}

    public void addQuery(Query query) {
        config.getQueries().add(query);
        saveConfiguration();
    }
    public void updateQuery(int index, Query query) {
        if(index >= 0 && index < config.getQueries().size()) {
            config.getQueries().set(index, query);
            saveConfiguration();
        }
    }

    public void removeQuery(int index) {
        if(index >= 0 && index < config.getQueries().size()) {
            config.getQueries().remove(index);
            saveConfiguration();
        }
    }

    public SchedulerConfig getConfig() {
        return config;
    }

    public void updateDatabase(int index, Database updatedDatabase) {
        if(index >= 0 && index < config.getDatabases().size()) {
            config.getDatabases().set(index, updatedDatabase);
            saveConfiguration();
        }
    }

    public void removeDatabase(int index) {
        if(index >= 0 && index < config.getDatabases().size()) {
            config.getDatabases().remove(index);
            saveConfiguration();
        }
    }
    public List<Database> getDatabases() {
        return config.getDatabases();
    }

}