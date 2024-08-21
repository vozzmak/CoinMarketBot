package com.telegram.coinmarketbot.services;

import com.telegram.coinmarketbot.models.CoinModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.MathContext;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {

    private static final String API_KEY = "";
    private static final String API_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";

    public static String getCoinsRateByNameOrSymbol(String nameOrSymbol, CoinModel model) throws IOException {
        return getCoinsRate(model, nameOrSymbol);
    }

    private static String getCoinsRate(CoinModel model, String nameOrSymbol) throws IOException {
        URI uri;
        try {
            uri = new URI(API_URL);
        } catch (URISyntaxException e) {
            log.error("Invalid URI Syntax: ", e);
            throw new RuntimeException("Failed to construct API URI", e);
        }

        URL url = uri.toURL();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-CMC_PRO_API_KEY", API_KEY);
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            log.error("API Request failed with response code: " + responseCode);
            throw new IOException("API Request failed with response code: " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JSONObject object = new JSONObject(result.toString());
            JSONArray dataArray = object.getJSONArray("data");

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject coinData = dataArray.getJSONObject(i);

                boolean matches = coinData.getString("name").equalsIgnoreCase(nameOrSymbol) ||
                        coinData.getString("symbol").equalsIgnoreCase(nameOrSymbol);

                if (matches) {
                    model.setId(coinData.getLong("id"));
                    model.setName(coinData.getString("name"));
                    model.setSymbol(coinData.getString("symbol"));
                    model.setPrice(coinData.getJSONObject("quote").getJSONObject("USD").getBigDecimal("price"));

                    return "CoinMarketCap rate of " + model.getName() + " is " + model.getPrice().setScale(4, MathContext.DECIMAL128.getRoundingMode()) + " USD.";
                }
            }

            return "No cryptocurrency found with the given name or symbol: " + nameOrSymbol;
        } finally {
            connection.disconnect();
        }
    }
}

