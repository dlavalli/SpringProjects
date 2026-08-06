package com.lavalliere.daniel.spring.nullspecify.controller;

import com.lavalliere.daniel.spring.nullspecify.domain.User;
import com.lavalliere.daniel.spring.nullspecify.orders.Order;
import com.lavalliere.daniel.spring.nullspecify.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestParam String email, @RequestParam String promoCode) {

        Order order = orderService.createOrder(email, promoCode);

        // Having the service declare @Nullable with @NullMarked on the package,
        // make it that IntelliJ will indicate the  possible NullPointerException when you hover over the firstName method
        if (order.promoCode().equalsIgnoreCase("hohoho")) {
            log.info("Will apply promo codes");
        }

        return order;
    }
}
