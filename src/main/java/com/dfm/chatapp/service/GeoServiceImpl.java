package com.dfm.chatapp.service;

import com.dfm.chatapp.service.geo.GeoResponse;
import com.dfm.chatapp.service.geo.GeoServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Service
public class GeoServiceImpl implements GeoService {

    private GeoServiceApi geoServiceApi = new GeoServiceApi();

    private Semaphore rateLimiter = new Semaphore(1);
    private static final Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);

    @Override
    public Optional<GeoResponse> geoCode(String address) {
        Optional<GeoResponse> response = Optional.empty();
        try {
            // Acquire a permit, waiting up to 1 second
            if (rateLimiter.tryAcquire(1, TimeUnit.SECONDS)) {
                try {
                    response = geoServiceApi.geoCode(address);
                } finally {
                    // Release the permit
                    rateLimiter.release();
                }
            } else {
                throw new RuntimeException("Rate limit exceeded. Please wait and try again.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Rate limit error.", e);
        } catch (Exception e) {
            throw new RuntimeException("Error geocoding address.", e);
        }
        return response;
    }
    @Override
    public double calculateDistance(String srcAddress, String refAddress) {
        double distance = geoServiceApi.calculate(srcAddress, refAddress);
        if(distance < 0) {
            logger.error("Failed to geocode one or both addresses.");
        }
        return distance;
    }

}
