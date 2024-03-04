package com.dfm.chatapp.service;

import com.dfm.chatapp.service.geo.GeoResponse;

import java.util.Optional;

public interface GeoService {
    Optional<GeoResponse> geoCode(String address);
    double calculateDistance(String source, String reference);
}
