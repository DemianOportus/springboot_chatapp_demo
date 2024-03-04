package com.dfm.chatapp.service.geo.api.google.response;

import com.dfm.chatapp.service.geo.GeoResponse;

public class GoogleMapsResponse implements GeoResponse {
    private double lat;
    private double lon;

    public GoogleMapsResponse(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public double getLatitude() {
        return lat;
    }

    @Override
    public double getLongitude() {
        return lon;
    }
}
