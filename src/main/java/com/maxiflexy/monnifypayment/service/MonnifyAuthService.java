package com.maxiflexy.monnifypayment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxiflexy.monnifypayment.config.MonnifyConfig;
import com.maxiflexy.monnifypayment.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonnifyAuthService {

    private final MonnifyConfig monnifyConfig;

    public String getAccessToken() throws Exception {
        String auth = monnifyConfig.getApiKey() + ":" + monnifyConfig.getSecretKey();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(monnifyConfig.getBaseUrl() + "/api/v1/auth/login");
            httpPost.setHeader("Authorization", "Basic " + encodedAuth);

            var response = client.execute(httpPost);
            var entity = response.getEntity();
            var responseString = EntityUtils.toString(entity);

            log.info("response for the auth service: {}", responseString);

            ObjectMapper objectMapper = new ObjectMapper();
            AuthResponse authResponse = objectMapper.readValue(responseString, AuthResponse.class);

            return authResponse.getResponseBody().getAccessToken();
        }
    }
}
