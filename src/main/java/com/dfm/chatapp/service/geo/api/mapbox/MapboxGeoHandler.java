package com.dfm.chatapp.service.geo.api.mapbox;

import com.dfm.chatapp.service.geo.GeoDistance;
import com.dfm.chatapp.service.geo.GeoHandler;
import com.dfm.chatapp.service.geo.GeoResponse;
import com.dfm.chatapp.service.geo.api.mapbox.request.UrlBuilder;
import com.dfm.chatapp.service.geo.api.mapbox.response.MapboxResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class MapboxGeoHandler implements GeoHandler {
    private static final Logger logger = LoggerFactory.getLogger(MapboxGeoHandler.class);
    private GeoDistance distanceCalculator;
    private String accessToken;

    public MapboxGeoHandler(String accessToken, GeoDistance distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
        this.accessToken = accessToken;
    }

    @Override
    public Optional<GeoResponse> geoCode(String address) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            String url = new UrlBuilder(address, accessToken).build();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String responseString = EntityUtils.toString(httpEntity);

            Gson gson = new Gson();
            JsonObject responseJson = gson.fromJson(responseString, JsonObject.class);

            if (responseJson.has("features") && responseJson.get("features").getAsJsonArray().size() > 0) {
                JsonArray coordinates = responseJson.get("features")
                        .getAsJsonArray()
                        .get(0)
                        .getAsJsonObject()
                        .get("geometry")
                        .getAsJsonObject()
                        .get("coordinates")
                        .getAsJsonArray();

                double longitude = coordinates.get(0).getAsDouble();
                double latitude = coordinates.get(1).getAsDouble();

                return Optional.of(new MapboxResponse(latitude, longitude));
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
