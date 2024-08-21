package com.example.demo.model.dto;

import java.util.Map;

public class NaverResponse {
    private String resultcode;
    private String message;
    private Map<String, String> response;
    private String email;
    private String name;

    public NaverResponse() {}

    public String getResultcode() {
        return resultcode;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getResponse() {
        return response;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NaverResponse{" +
                "resultcode='" + resultcode + '\'' +
                ", message='" + message + '\'' +
                ", response=" + response +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
