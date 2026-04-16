package com.xchange.model;

import com.xchange.app.Navigator;
import com.xchange.repository.Database;

import java.time.LocalDate;

public class Graph {
 
    private String graphName;
    private String graphDescription;
    private int graphImportance;
    private LocalDate dateCreated;
    private String curFromCode;
    private String curToCode;
    private LocalDate startDate;
    private LocalDate endDate;

    public Graph(String curFromCode, String curToCode, LocalDate startDate, LocalDate endDate) {
        this.curFromCode = curFromCode;
        this.curToCode = curToCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Graph(String graphName, String graphDescription, int graphImportance, LocalDate dateCreated, String curFromCode, String curToCode, LocalDate startDate, LocalDate endDate) {
        this.graphName = graphName;
        this.graphDescription = graphDescription;
        this.graphImportance = graphImportance;
        this.dateCreated = dateCreated;
        this.curFromCode = curFromCode;
        this.curToCode = curToCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCurFromCode() {
        return curFromCode;
    }

    public String getCurToCode() {
        return curToCode;
    }
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getGraphDescription() {
        return graphDescription;
    }

    public int getGraphImportance() {
        return graphImportance;
    }

    public String getGraphName() {
        return graphName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void saveGraph(String graphName, int graphImportance, String graphDescription) {
        this.graphName = graphName;
        this.graphImportance = graphImportance;
        this.graphDescription = graphDescription;
        this.dateCreated = LocalDate.now();
        Navigator.getUser().getGraphs().add(this);
        Database.saveGraph(this, Navigator.getUser().getUserID());
    }
}