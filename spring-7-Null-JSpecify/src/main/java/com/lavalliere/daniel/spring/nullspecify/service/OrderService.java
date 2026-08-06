package com.lavalliere.daniel.spring.nullspecify.service;

import com.lavalliere.daniel.spring.nullspecify.orders.Order;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    public Order createOrder(String email, @Nullable String promoCode) {
        sendEmailConfirmation(email);

        //if (promoCode != null) {
            applyDiscount(email, promoCode);
        //}
        return new Order(email, promoCode);
    }

    private void  sendEmailConfirmation(String email) {
      log.info("Sending email confirmation {} ", email);
    }

    private void applyDiscount(String email, String promoCode) {
        log.info("Applying discount for promo code {} to {}", promoCode, email);
    }
}
