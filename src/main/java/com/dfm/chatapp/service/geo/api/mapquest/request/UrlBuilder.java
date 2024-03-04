package com.dfm.chatapp.service.geo.api.mapquest.request;

public class UrlBuilder {
    private static final String MAPQUEST_API_BASE_URL = "http://www.mapquestapi.com/geocoding/v1/address";
    private String address;
    private String apiKey;

    public UrlBuilder(String address, String apiKey) {
        this.address = address;
        this.apiKey = apiKey;
    }

    public String build() {
        StringBuilder urlBuilder = new StringBuilder(MAPQUEST_API_BASE_URL);
        urlBuilder.append("?key=")
                .append(apiKey)
                .append("&location=")
                .append(address.replaceAll(" ", "%20"))
                .append("&maxResults=1");
        return urlBuilder.toString();
    }
}
