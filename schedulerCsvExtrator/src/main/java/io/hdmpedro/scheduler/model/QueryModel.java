package io.hdmpedro.scheduler.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class QueryModel implements Serializable {
    @XmlElement(name = "database-ref")
    private String databaseRef;

    @XmlElement(name = "sql")
    private String sql;

    @XmlElement(name = "csv")
    private CsvModel csv;

    public String getDatabaseRef() { return databaseRef; }
    public String getSql() { return sql; }
    public CsvModel getCsv() { return csv; }
}
