package com.dfm.chatapp.service.geo;

import java.util.Optional;

public interface GeoHandler {
    Optional<GeoResponse> geoCode(String address);
    double calculate(GeoResponse geocode1, GeoResponse geocode2);
}
