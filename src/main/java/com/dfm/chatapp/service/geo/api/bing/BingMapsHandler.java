package com.dfm.chatapp.service.geo.api.bing;

import com.dfm.chatapp.service.geo.GeoDistance;
import com.dfm.chatapp.service.geo.GeoHandler;
import com.dfm.chatapp.service.geo.GeoResponse;
import com.dfm.chatapp.service.geo.api.bing.request.UrlBuilder;
import com.dfm.chatapp.service.geo.api.bing.response.BingMapsResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;


public class BingMapsHandler implements GeoHandler {

    private static final Logger logger = LoggerFactory.getLogger(BingMapsHandler.class);

    private GeoDistance distanceCalculator;
    private String apiKey;

    public BingMapsHandler(String apiKey, GeoDistance distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
        this.apiKey = apiKey;
    }

    @Override
    public Optional<GeoResponse> geoCode(String address) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            String url = new UrlBuilder(address, apiKey).build();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String responseString = EntityUtils.toString(httpEntity);

            Gson gson = new Gson();
            JsonObject responseJson = gson.fromJson(responseString, JsonObject.class);

            if (responseJson.has("resourceSets") && responseJson.get("resourceSets").getAsJsonArray().size() > 0) {
                JsonArray resources = responseJson.get("resourceSets")
                        .getAsJsonArray()
                        .get(0)
                        .getAsJsonObject()
                        .get("resources")
                        .getAsJsonArray();

                if (resources.size() > 0) {
                    JsonObject location = resources.get(0).getAsJsonObject().get("point").getAsJsonObject();
                    double latitude = location.get("coordinates").getAsJsonArray().get(0).getAsDouble();
                    double longitude = location.get("coordinates").getAsJsonArray().get(1).getAsDouble();
                    return Optional.of(new BingMapsResponse(latitude, longitude));
                }
            }
        } catch (IOException  e) {
            logger.error("Invalid Response", e);
        }
        return Optional.empty();
    }

    @Override
    public double calculate(GeoResponse point1, GeoResponse point2) {
        return distanceCalculator.calculate(point1, point2);
    }
}