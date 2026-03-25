package com.example.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<String> getOrders() {
        return Arrays.asList("Order1", "Order2");
    }

    @GetMapping("/pay")
    public String callPaymentService() {
        String paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/pay", String.class);
        return "Order Service called Payment Service. Response: " + paymentResponse;
    }
}
