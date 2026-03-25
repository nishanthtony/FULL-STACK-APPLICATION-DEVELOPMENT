package com.week8.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from User-MS!";
    }

    @GetMapping("/send-message/{message}")
    public String sendMessage(@PathVariable String message) {
        jmsTemplate.convertAndSend("test-queue", message);
        return "Message sent to ActiveMQ: " + message;
    }
}
