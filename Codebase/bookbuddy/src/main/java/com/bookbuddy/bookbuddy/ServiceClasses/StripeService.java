package com.bookbuddy.bookbuddy.ServiceClasses;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Entities.PaymentResult;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import jakarta.annotation.PostConstruct;


@Service
public class StripeService {

    @Value("${api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init(){
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency) {
        Stripe.apiKey = stripeApiKey;

        List<Object> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("payment_method_types", paymentMethodTypes);

        try {
            return PaymentIntent.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean wasPaymentSuccessful(String paymentIntentId) {
        Stripe.apiKey = stripeApiKey;

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return "succeeded".equals(paymentIntent.getStatus());
        } catch (StripeException e) {
            // Log and handle exception
            return false;
        }
    }

    public PaymentResult processPayment(BigDecimal totalPrice, String paymentToken) {
        Long totalPriceInCents = totalPrice.multiply(new BigDecimal(100)).longValueExact();

        Map<String, Object> params = new HashMap<>();
        params.put("amount", totalPriceInCents);
        params.put("currency", "usd");
        params.put("description", "Order payment");
        params.put("payment_method", paymentToken);
        params.put("confirm", true);
        try {
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            if ("succeeded".equals(paymentIntent.getStatus())) {
                return new PaymentResult(true, null, paymentIntent.getId());
            } else {
                return new PaymentResult(false, paymentIntent.getLastPaymentError().getMessage(), paymentIntent.getId());
            }
        } catch (StripeException e) {
            return new PaymentResult(false, e.getMessage(), null);
        }
    }




}