package com.dfm.chatapp.service.geo.api.bing.request;

public class UrlBuilder {
    private static final String BING_MAPS_API_BASE_URL = "http://dev.virtualearth.net/REST/v1/Locations";
    private String address;
    private String apiKey;

    public UrlBuilder(String address, String apiKey) {
        this.address = address;
        this.apiKey = apiKey;
    }

    public String build() {
        StringBuilder urlBuilder = new StringBuilder(BING_MAPS_API_BASE_URL);
        urlBuilder.append("?q=")
                .append(address.replaceAll(" ", "%20"))
                .append("&key=")
                .append(apiKey)
                .append("&maxResults=1");
        return urlBuilder.toString();
    }
}
