package com.dfm.chatapp.service.geo.api.google.request;

public class UrlBuilder {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
    private String address;
    private String apiKey;

    public UrlBuilder(String address, String apiKey) {
        this.address = address;
        this.apiKey = apiKey;
    }

    public UrlBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public UrlBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String build() {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address must be provided.");
        }
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key must be provided.");
        }

        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("address=").append(address.replace(" ", "+"));
        urlBuilder.append("&key=").append(apiKey);

        return urlBuilder.toString();
    }
}
