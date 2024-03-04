package com.dfm.chatapp.service.geo.api.nominatim.response;

import com.dfm.chatapp.service.geo.GeoResponse;

public class NominatimResponse implements GeoResponse {
    private double lat;
    private double lon;

    @Override
    public double getLatitude() {
        return lat;
    }

    public void setLatitude(double lat) {
        this.lat = lat;
    }

    @Override
    public double getLongitude() {
        return lon;
    }

    public void setLongitude(double lon) {
        this.lon = lon;
    }

    public String getCoordinatesAsString() {
        return "Latitude: " + lat + ", Longitude: " + lon;
    }
}
