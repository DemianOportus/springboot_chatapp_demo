package com.dfm.chatapp.service.geo.api.google;

import com.dfm.chatapp.service.geo.GeoDistance;
import com.dfm.chatapp.service.geo.GeoHandler;
import com.dfm.chatapp.service.geo.GeoResponse;
import com.dfm.chatapp.service.geo.api.google.request.UrlBuilder;
import com.dfm.chatapp.service.geo.api.google.response.GoogleMapsResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class GoogleMapsHandler implements GeoHandler {

    private static final Logger logger = LoggerFactory.getLogger(GoogleMapsHandler.class);

    private GeoDistance distanceCalculator;

    private String apiKey;

    public GoogleMapsHandler(String apiKey, GeoDistance distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
        this.apiKey = apiKey;
    }

    @Override
    public Optional<GeoResponse> geoCode(String address) {
        HttpClient httpClient = HttpClients.createDefault();
        String url = new UrlBuilder(address, apiKey).build();
        HttpGet httpGet = new HttpGet();
        GoogleMapsResponse response = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String responseString = EntityUtils.toString(httpEntity);
            Gson gson = new Gson();
            JsonObject responseJson = gson.fromJson(responseString, JsonObject.class);

            if (responseJson.has("results") && responseJson.get("results").getAsJsonArray().size() > 0) {
                JsonObject location = responseJson.get("results")
                        .getAsJsonArray()
                        .get(0)
                        .getAsJsonObject()
                        .get("geometry")
                        .getAsJsonObject()
                        .get("location")
                        .getAsJsonObject();

                double latitude = location.get("lat").getAsDouble();
                double longitude = location.get("lng").getAsDouble();
                response = new GoogleMapsResponse(latitude, longitude);
            }

        } catch (ClientProtocolException e) {
            logger.error("Invalid Protocol", e);
        } catch (IOException e) {
            logger.error("Invalid Response", e);
        }

        if (response != null) {
            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public double calculate(GeoResponse geocode1, GeoResponse geocode2) {
        return distanceCalculator.calculate(geocode1, geocode2);
    }
}