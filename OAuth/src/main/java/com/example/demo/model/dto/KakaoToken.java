package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String token_type;
    @JsonProperty("expires_in")
    private String expires_in;
    @JsonProperty("refresh_token")
    private String refresh_token;
    @JsonProperty("scope")
    private String scope;

    public KakaoToken() {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "kakaoToken{" +
                "accessToken='" + accessToken + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
