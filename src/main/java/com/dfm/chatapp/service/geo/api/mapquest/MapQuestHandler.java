package com.dfm.chatapp.service.geo.api.mapquest;

import com.dfm.chatapp.service.geo.GeoDistance;
import com.dfm.chatapp.service.geo.GeoHandler;
import com.dfm.chatapp.service.geo.GeoResponse;
import com.dfm.chatapp.service.geo.api.mapquest.request.UrlBuilder;
import com.dfm.chatapp.service.geo.api.mapquest.response.MapQuestResponse;
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
public class MapQuestHandler implements GeoHandler {

    private static final Logger logger = LoggerFactory.getLogger(MapQuestHandler.class);
    private GeoDistance distanceCalculator;
    private String apiKey;

    public MapQuestHandler(String apiKey, GeoDistance distanceCalculator) {
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

            if (responseJson.has("results") && responseJson.get("results").getAsJsonArray().size() > 0) {
                JsonArray locations = responseJson.get("results")
                        .getAsJsonArray()
                        .get(0)
                        .getAsJsonObject()
                        .get("locations")
                        .getAsJsonArray();

                if (locations.size() > 0) {
                    JsonObject latLng = locations.get(0).getAsJsonObject().get("latLng").getAsJsonObject();
                    double latitude = latLng.get("lat").getAsDouble();
                    double longitude = latLng.get("lng").getAsDouble();
                    return Optional.of(new MapQuestResponse(latitude, longitude));
                }
            }
        } catch (IOException e) {
            logger.error("Invalid Response", e);
        }
        return Optional.empty();
    }

    @Override
    public double calculate(GeoResponse point1, GeoResponse point2) {
        return distanceCalculator.calculate(point1, point2);
    }
}
