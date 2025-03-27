package io.hdmpedro.scheduler.agendador.model;

public class Query {
    private String databaseRef;
    private String sql;
    private String outputPath;
    private String fileName;
    private String delimiter;

    public String getDatabaseRef() { return databaseRef; }
    public void setDatabaseRef(String databaseRef) { this.databaseRef = databaseRef; }
    public String getSql() { return sql; }
    public void setSql(String sql) { this.sql = sql; }
    public String getOutputPath() { return outputPath; }
    public void setOutputPath(String outputPath) { this.outputPath = outputPath; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getDelimiter() { return delimiter; }
    public void setDelimiter(String delimiter) { this.delimiter = delimiter; }
}