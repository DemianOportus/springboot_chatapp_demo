package com.dfm.chatapp.service.geo.api;

public enum ServiceProviderType {
    BING_MAPS("com.dfm.chatapp.service.geo.api.bing.BingMapsGeoHandler"),
    MAPBOX("com.dfm.chatapp.service.geo.api.mapbox.MapboxHandler"),
    MAPQUEST("com.dfm.chatapp.service.geo.api.mapquest.MapQuestGeoHandler"),
    NOMINATIM("com.dfm.chatapp.service.geo.api.nominatim.NominatimHandler"),
    GOOGLE_MAPS("com.dfm.chatapp.service.geo.api.google.GoogleMapsHandler");

    private final String className;

    ServiceProviderType(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
