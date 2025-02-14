package com.maxiflexy.monnifypayment.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonnifyConfig {

    @Value("${monnify.apiKey}")
    private String apiKey;

    @Value("${monnify.secretKey}")
    private String secretKey;

    @Value("${monnify.contractCode}")
    private String contractCode;

    @Value("${monnify.baseUrl}")
    private String baseUrl;

    @Value("${monnify.redirectUrl}")
    private String redirectUrl;
}
