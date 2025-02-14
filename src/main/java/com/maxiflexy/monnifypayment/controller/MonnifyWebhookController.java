package com.maxiflexy.monnifypayment.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/monnify/webhook")
public class MonnifyWebhookController {

    @PostMapping
    public String handleWebhook(@RequestBody String webhookPayload) {
        try {
            // Parse the webhook payload
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode payloadJson = objectMapper.readTree(webhookPayload);

            String paymentStatus = payloadJson.get("eventData").get("paymentStatus").asText();
            String paymentReference = payloadJson.get("eventData").get("paymentReference").asText();

            if ("PAID".equalsIgnoreCase(paymentStatus)) {
                // Handle successful payment
                System.out.println("\n");
                System.out.println("\n");
                System.out.println("\n");
                System.out.println("Payment successful for reference!!!!!!!: " + paymentReference);
            } else {
                // Handle failed or pending payment
                System.out.println("\n");
                System.out.println("\n");
                System.out.println("\n");
                System.out.println("Payment failed or pending for reference: " + paymentReference);
            }

            return "Webhook received and processed";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to process webhook: " + e.getMessage();
        }
    }
}
