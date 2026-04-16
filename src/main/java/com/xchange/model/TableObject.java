package com.xchange.model;

import java.time.LocalDate;

import javafx.scene.control.Button;

public class TableObject {
    
    public String name;
    public String description;
    public LocalDate date;
    public LocalDate dateFrom;
    public LocalDate dateTo;
    public int importance;
    public String currencyFrom;
    public String currencyTo;
    public Button view;
    public Button download;
    public Button remove;

    public TableObject(String name, String description, LocalDate date, LocalDate dateFrom, LocalDate dateTo, String currencyFrom, String currencyTo, int importance, Button view, Button download, Button remove) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.importance = importance;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.view = view;
        this.download = download;
        this.remove = remove;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalDate getDateFrom() {
        return dateFrom;
    }
    public LocalDate getDateTo() {
        return dateTo;
    }
    public int getImportance() {
        return importance;
    }
    public String getCurrencyFrom() {
        return currencyFrom;
    }
    public String getCurrencyTo() {
        return currencyTo;
    }
    public Button getDownload() {
        return download;
    }
    public Button getRemove() {
        return remove;
    }
    public Button getView() {
        return view;
    }
    
}
