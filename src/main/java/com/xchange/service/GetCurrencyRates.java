package com.xchange.service;

import com.xchange.repository.Database;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Calculator
 */

public class GetCurrencyRates {

    public static double calculateLatest(String currencyFromCode, String currencyToCode) throws Exception {
        Double x = GetRatesAPI.requestLatest(currencyFromCode);
        Double y = GetRatesAPI.requestLatest(currencyToCode);
        return y/x;
    }

    public static ArrayList<Double> calculateHistorical(String currencyFromCode, String currencyToCode, LocalDate startDate, LocalDate endDate) {
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<Double> fromValues = Database.getCurrencyValuesBetween(currencyFromCode, startDate, endDate);
        ArrayList<Double> toValues = Database.getCurrencyValuesBetween(currencyToCode, startDate, endDate);

        int valuesSize;
        if (fromValues.size() > toValues.size()) {
            valuesSize = toValues.size();
        }
        else {
            valuesSize = fromValues.size();
        }
        
        for (int i = 0; i < valuesSize; i++) {
            values.add(toValues.get(i) / fromValues.get(i));
        }

        return values;
    }

    public static ArrayList<LocalDate> getHistoricalDates(ArrayList<Double> values, LocalDate endDate) {
        ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
        LocalDate startFrom = endDate.minusDays(values.size() - 1);

        for (int i = 0; i < values.size(); i++) {
            dates.add(startFrom);
            startFrom = startFrom.plusDays(1);
        }

        return dates;
    }

    public static void saveHistoricalData() throws Exception {
        LocalDate date = Database.getLatestDate().plusDays(1);        
        while(!date.isAfter(LocalDate.now())){
            for(String currency : Database.getCurrencies()){
                Double rate = GetRatesAPI.requestHistorical(date, currency);
                Database.insertCurrencyValue(currency, date, rate);
            }
            date = date.plusDays(1);
        }
    }
    

}