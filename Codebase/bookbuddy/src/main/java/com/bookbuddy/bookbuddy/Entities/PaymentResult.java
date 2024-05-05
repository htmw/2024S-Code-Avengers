package com.bookbuddy.bookbuddy.Entities;

public class PaymentResult {
    private boolean successful;
    private String errorMessage;
    private String paymentIntentId;

    public PaymentResult(boolean successful, String errorMessage, String paymentIntentId) {
        this.successful = successful;
        this.errorMessage = errorMessage;
        this.paymentIntentId = paymentIntentId;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    // You can add more fields and methods as needed, for example, to include more details about the payment.
}
