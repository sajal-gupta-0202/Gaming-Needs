package com.salesSavvy.DTO;

public class PaymentVerifyRequest {
    public String orderId;
    public String paymentId;
    public String signature;
    public String username;

    public String getOrderId() { return orderId; }
    public String getPaymentId() { return paymentId; }
    public String getSignature() { return signature; }
    public String getUsername() { return username; }
}