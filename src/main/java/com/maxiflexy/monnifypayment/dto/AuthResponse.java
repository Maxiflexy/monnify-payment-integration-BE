package com.maxiflexy.monnifypayment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {

    @JsonProperty("requestSuccessful")
    private boolean requestSuccessful;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseBody")
    private ResponseBody responseBody;

    @Data
    public static class ResponseBody {

        @JsonProperty("accessToken")
        private String accessToken;

        @JsonProperty("expiresIn")
        private int expiresIn;
    }
}
