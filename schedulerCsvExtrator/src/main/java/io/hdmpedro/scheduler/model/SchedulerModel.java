/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.hdmpedro.scheduler.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *
 * @author DSK-11
 */
@XmlRootElement(name = "scheduler-config")
@XmlAccessorType(XmlAccessType.FIELD)
public class    SchedulerModel {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "host")
    private String host;

    @XmlElement(name = "cron-expression")
    private String cronExpression;

    @XmlElementWrapper(name = "databases")
    @XmlElement(name = "database")
    private List<DatabaseModel> databases;

    @XmlElement(name = "schedule")
    private SchedulerModel schedule;

    @XmlElementWrapper(name = "queries")
    @XmlElement(name = "query")
    private List<QueryModel> queries;

    public void setDatabases(List<DatabaseModel> databases) {
        this.databases = databases;
    }

    public void setQueries(List<QueryModel> queries) {
        this.queries = queries;
    }

    public void setSchedule(SchedulerModel schedule) {
        this.schedule = schedule;
    }

    public List<DatabaseModel> getDatabases() { return databases; }
    public SchedulerModel getSchedule() { return schedule; }
    public List<QueryModel> getQueries() { return queries; }

    public String getCronExpression() {
        return cronExpression;
    }

    
}