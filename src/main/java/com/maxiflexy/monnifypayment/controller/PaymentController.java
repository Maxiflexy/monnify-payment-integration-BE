package com.maxiflexy.monnifypayment.controller;

import com.maxiflexy.monnifypayment.dto.PaymentRequest;
import com.maxiflexy.monnifypayment.service.MonnifyPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private final MonnifyPaymentService paymentService;

    @PostMapping("/initiate")
    public String initiatePayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            String paymentReference = "REF-" + UUID.randomUUID().toString();
            paymentRequest.setPaymentReference(paymentReference);

            String checkoutUrl = paymentService.initiateTransaction(paymentRequest);
            return "Payment initiated successfully. Redirect to: " + checkoutUrl;

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to initiate payment: " + e.getMessage();
        }
    }

}
