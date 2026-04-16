package com.xchange.service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import org.json.JSONObject;

public class GetRatesAPI {
    private static final String API_KEY_PLACEHOLDER = "YOUR_API_KEY_HERE";
    private static final String APP_ID = readAppId();

    private static String readAppId() {
        String envAppId = System.getenv("OPENEXCHANGE_APP_ID");
        if (envAppId != null && !envAppId.isBlank()) {
            return envAppId.trim();
        }
        return API_KEY_PLACEHOLDER;
    }

    private static void validateApiKey() {
        if (API_KEY_PLACEHOLDER.equals(APP_ID)) {
            throw new IllegalStateException(
                "Missing OpenExchangeRates app id. Set OPENEXCHANGE_APP_ID before running."
            );
        }
    }

    public static double requestLatest(String currency) throws Exception {
        validateApiKey();
        URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=" + APP_ID + "&show_alternative=1&symbols=" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        return parseJsonResponse(response.toString(), currency);
    }

    public static double requestHistorical(LocalDate date, String currency) throws Exception {
        validateApiKey();
        URL url = new URL("https://openexchangerates.org/api/historical/" + date + ".json?app_id=" + APP_ID + "&show_alternative=1&symbols=" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        return parseJsonResponse(response.toString(), currency);
    }

    private static double parseJsonResponse(String jsonResponse, String currency) {
        JSONObject json = new JSONObject(jsonResponse);
        return json.getJSONObject("rates").getDouble(currency);
    }
}

