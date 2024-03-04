package com.dfm.chatapp.service.geo.distance;

import com.dfm.chatapp.service.geo.GeoDistance;
import com.dfm.chatapp.service.geo.GeoResponse;

public class HaversineDistance implements GeoDistance {
    private static final double EARTH_RADIUS = 6371.0; // Earth's radius in kilometers

    @Override
    public double calculate(GeoResponse src, GeoResponse dest) {
        double lat1 = Math.toRadians(src.getLatitude());
        double lon1 = Math.toRadians(src.getLongitude());
        double lat2 = Math.toRadians(dest.getLatitude());
        double lon2 = Math.toRadians(dest.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
