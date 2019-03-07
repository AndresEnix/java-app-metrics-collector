package com.andres.metrics.collector.model;

import java.util.List;

public class Metric {

    private String name;
    private Boolean enabled;
    private String cronTab;
    private String statement;
    private int queryTimeOut;
    private int queryMaxRows;
    private MetricType type;
    private List<Parameter> parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCronTab() {
        return cronTab;
    }

    public void setCronTab(String cronTab) {
        this.cronTab = cronTab;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public int getQueryTimeOut() {
        return queryTimeOut;
    }

    public void setQueryTimeOut(int queryTimeOut) {
        this.queryTimeOut = queryTimeOut;
    }

    public int getQueryMaxRows() {
        return queryMaxRows;
    }

    public void setQueryMaxRows(int queryMaxRows) {
        this.queryMaxRows = queryMaxRows;
    }

    public MetricType getType() {
        return type;
    }

    public void setType(MetricType type) {
        this.type = type;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
}
