package com.maxiflexy.monnifypayment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxiflexy.monnifypayment.config.MonnifyConfig;
import com.maxiflexy.monnifypayment.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonnifyPaymentService {

    private final MonnifyAuthService authService;
    private final MonnifyConfig monnifyConfig;
    private final ObjectMapper objectMapper;


    public String initiateTransaction(PaymentRequest paymentRequest) throws Exception {
        paymentRequest.setCurrencyCode("NGN");
        paymentRequest.setContractCode(monnifyConfig.getContractCode());

        log.info("Payment Request: {}", paymentRequest);

        String accessToken = authService.getAccessToken();
        log.info("Access token: {}", accessToken);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(monnifyConfig.getBaseUrl() + "/api/v1/merchant/transactions/init-transaction");
            httpPost.setHeader("Authorization", "Bearer " + accessToken);
            httpPost.setHeader("Content-Type", "application/json");

            String jsonPayload = objectMapper.writeValueAsString(paymentRequest);
            httpPost.setEntity(new StringEntity(jsonPayload));

            var response = client.execute(httpPost);
            var responseString = EntityUtils.toString(response.getEntity());
            log.info("response from Monnify: {}", responseString);

            // Parse the response
            JsonNode responseJson = objectMapper.readTree(responseString);

            // Check if request was successful
            boolean requestSuccessful = responseJson.get("requestSuccessful").asBoolean();
            if (!requestSuccessful) {
                String responseMessage = responseJson.get("responseMessage").asText();
                throw new IllegalStateException("Transaction initiation failed: " + responseMessage);
            }

            // Extract checkout URL
            JsonNode responseBodyNode = responseJson.get("responseBody");
            if (responseBodyNode == null || responseBodyNode.get("checkoutUrl") == null) {
                throw new IllegalStateException("Missing 'responseBody' or 'checkoutUrl' in the response: " + responseString);
            }

            return responseBodyNode.get("checkoutUrl").asText();
        }
    }


}
