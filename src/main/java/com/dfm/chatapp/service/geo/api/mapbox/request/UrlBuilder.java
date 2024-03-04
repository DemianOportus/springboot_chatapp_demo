package com.dfm.chatapp.service.geo.api.mapbox.request;

public class UrlBuilder {
    private static final String MAPBOX_API_BASE_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private String address;
    private String accessToken;

    public UrlBuilder(String address, String accessToken) {
        this.address = address;
        this.accessToken = accessToken;
    }

    public String build() {
        StringBuilder urlBuilder = new StringBuilder(MAPBOX_API_BASE_URL);
        urlBuilder.append(address.replaceAll(" ", "%20"))
                .append(".json?access_token=")
                .append(accessToken)
                .append("&limit=1");
        return urlBuilder.toString();
    }
}
