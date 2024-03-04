package com.dfm.chatapp.service.geo;

import com.dfm.chatapp.service.geo.api.ServiceProviderType;
import com.dfm.chatapp.service.geo.api.bing.BingMapsHandler;
import com.dfm.chatapp.service.geo.api.google.GoogleMapsHandler;
import com.dfm.chatapp.service.geo.api.mapbox.MapboxGeoHandler;
import com.dfm.chatapp.service.geo.api.mapquest.MapQuestHandler;
import com.dfm.chatapp.service.geo.api.nominatim.NominatimHandler;
import com.dfm.chatapp.service.geo.distance.HaversineDistance;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class GeoServiceApi {

    private GeoHandler geoServiceHandler;

    private final Properties properties;

    public GeoServiceApi() {
        this(ServiceProviderType.MAPQUEST);
    }

    public GeoServiceApi(ServiceProviderType serviceProvider) {
        properties = new Properties();
        // TODO: Fix the code logic to use spring env
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:application.properties");
            InputStream input = resource.getInputStream();
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (serviceProvider == ServiceProviderType.GOOGLE_MAPS) {
            String apiKey = properties.getProperty("google.maps.api.key");
            geoServiceHandler =  new GoogleMapsHandler(apiKey, new HaversineDistance());
        } else if (serviceProvider == ServiceProviderType.MAPBOX) {
            String accessToken = properties.getProperty("mapbox.maps.api.aceess.token");
            geoServiceHandler =  new MapboxGeoHandler(accessToken, new HaversineDistance());
        } else if (serviceProvider == ServiceProviderType.MAPQUEST) {
            String apiKey = properties.getProperty("mapquest.maps.api.key");
            geoServiceHandler =  new MapQuestHandler(apiKey, new HaversineDistance());
        } else if (serviceProvider == ServiceProviderType.BING_MAPS) {
            String apiKey = properties.getProperty("bing.maps.api.key");
            geoServiceHandler =  new BingMapsHandler(apiKey, new HaversineDistance());
        } else if (serviceProvider == ServiceProviderType.NOMINATIM) {
            geoServiceHandler =  new NominatimHandler(new HaversineDistance());
        }
    }

    public Optional<GeoResponse> geoCode(String address) {
        if (geoServiceHandler != null) {
            return geoServiceHandler.geoCode(address);
        }
        return Optional.empty();
    }

    public double calculate(GeoResponse point1, GeoResponse point2) {
        if (geoServiceHandler != null) {
            return geoServiceHandler.calculate(point1, point2);
        }
        return -1;
    }

    public double calculate(String srcAddress, String refAddress) {
        Optional<GeoResponse> src = geoCode(srcAddress);
        Optional<GeoResponse> ref = geoCode(refAddress);

        if (src.isPresent() && ref.isPresent()) {
            return calculate(src.get(), ref.get());
        }
        return -1;
    }
}
