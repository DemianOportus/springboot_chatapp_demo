package com.dfm.chatapp.service.geo.api.bing.response;

import com.dfm.chatapp.service.geo.GeoResponse;

public class BingMapsResponse implements GeoResponse {
    private double latitude;
    private double longitude;

    public BingMapsResponse(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }
}
