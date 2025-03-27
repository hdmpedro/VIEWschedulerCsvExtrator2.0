package io.hdmpedro.scheduler.agendador.model;

import java.util.ArrayList;
import java.util.List;

public class SchedulerConfig {
    private List<Database> databases = new ArrayList<>();
    private String cronExpression;
    private List<Query> queries = new ArrayList<>();

    public List<Database> getDatabases() { return databases; }
    public void setDatabases(List<Database> databases) { this.databases = databases; }
    public String getCronExpression() { return cronExpression; }
    public void setCronExpression(String cronExpression) { this.cronExpression = cronExpression; }
    public List<Query> getQueries() { return queries; }
    public void setQueries(List<Query> queries) { this.queries = queries; }
}