package com.dfm.chatapp.service.geo.api.nominatim;
import com.dfm.chatapp.service.geo.GeoDistance;
import com.dfm.chatapp.service.geo.GeoHandler;
import com.dfm.chatapp.service.geo.GeoResponse;
import com.dfm.chatapp.service.geo.api.nominatim.request.UrlBuilder;
import com.dfm.chatapp.service.geo.api.nominatim.response.NominatimResponse;
import com.google.gson.Gson;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class NominatimHandler implements GeoHandler {

    private static final Logger logger = LoggerFactory.getLogger(NominatimHandler.class);

    private GeoDistance distanceCalculator;

    public NominatimHandler(GeoDistance distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public Optional<GeoResponse> geoCode(String address) {
        HttpClient httpClient = HttpClients.createDefault();
        String url = new UrlBuilder(address).format("json").limit(1).build();
        HttpGet httpGet = new HttpGet();
        NominatimResponse[] responseArray = null;
        try {
            httpGet.setURI(new URI(url.replace(" ", "+")));
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String responseString = EntityUtils.toString(httpEntity);
            Gson gson = new Gson();
            responseArray = gson.fromJson(responseString, NominatimResponse[].class);
        } catch (URISyntaxException e) {
            logger.error("Invalid URI Syntax", e);
        } catch (ClientProtocolException e) {
            logger.error("Invalid Protocol", e);
        } catch (IOException e) {
            logger.error("Invalid Response", e);
        }

        if (responseArray != null && responseArray.length > 0) {
            return Optional.of(responseArray[0]);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public double calculate(GeoResponse point1, GeoResponse point2) {
        return distanceCalculator.calculate(point1, point2);
    }
}