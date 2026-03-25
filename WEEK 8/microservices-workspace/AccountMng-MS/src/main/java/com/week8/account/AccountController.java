package com.week8.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/user-info")
    public String getUserInfo() {
        try {
            // Using service name instead of hardcoded URL for LoadBalancing
            String response = restTemplate.getForObject("http://USER-MS/users/hello", String.class);
            return "Account Service received: " + response;
        } catch (Exception e) {
            return "Fallback: User Service is currently unavailable.";
        }
    }

    @JmsListener(destination = "test-queue")
    public void receiveMessage(String message) {
        System.out.println("Received message from ActiveMQ: " + message);
    }
}
