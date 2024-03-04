package com.dfm.chatapp.service.geo.api.nominatim.request;

public class UrlBuilder {
    private static final String BASE_URL = "https://nominatim.openstreetmap.org/search";
    private static final String DEFAULT_FORMAT = "json";

    private String address;
    private String format = DEFAULT_FORMAT;
    private int limit = 1;

    public UrlBuilder(String address) {
        this.address = address;
    }

    public UrlBuilder format(String format) {
        this.format = format;
        return this;
    }

    public UrlBuilder limit(int limit) {
        this.limit = limit;
        return this;
    }

    public String build() {
        StringBuilder urlBuilder = new StringBuilder(BASE_URL);
        urlBuilder.append("?format=").append(format)
                .append("&q=").append(address)
                .append("&limit=").append(limit);

        return urlBuilder.toString();
    }
}
