package com.maxiflexy.monnifypayment.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private double amount;
    private String customerName;
    private String customerEmail;
    private String paymentReference;
    private String paymentDescription;
    private String redirectUrl;
    private String currencyCode;
    private String contractCode;
    private IncomeSplitConfig incomeSplitConfig;

    @Data
    public static class IncomeSplitConfig {
        private String subAccountCode;
        private Boolean feeBearer;
        private Float feePercentage;
        private Float splitPercentage;
        private Float splitAmount;
    }
}
