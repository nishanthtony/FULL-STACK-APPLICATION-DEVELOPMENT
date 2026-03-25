# Microservices Implementation Guide

This project contains a complete Microservices-based system using Spring Boot, intended to run in Eclipse.

## 1. ECLIPSE SETUP STEPS
1. Open Eclipse IDE.
2. Go to `File -> Import... -> Maven -> Existing Maven Projects`.
3. Browse to the folder containing these generated projects (e.g., `WEEK 7` directory).
4. Select all 5 projects (`eureka-server`, `user-service`, `product-service`, `payment-service`, `order-service`) and click **Finish**.
*(Note: If you were creating them from scratch, you would go to `File -> New -> Spring Starter Project` and create each project independently).*

## 2. PROJECT CONFIGURATION
Each of the 5 projects has been generated with:
* **Project Type:** Maven
* **Language:** Java
* **Java Version:** 17
* **Packaging:** Jar

## 3. REQUIRED DEPENDENCIES
Dependencies have been added to the respective `pom.xml` files:
* **All Client Services:** Spring Web, Spring Boot DevTools, Lombok, Spring Cloud Netflix Eureka Client.
* **Service-to-Service Communication:** Spring Web (RestTemplate is used in `order-service`).
* **Eureka Server:** Spring Cloud Netflix Eureka Server.
* **Global:** Spring Cloud Starter Dependency Management.

## 4. PORT CONFIGURATION
Ports configured in respective `application.properties`:
* `user-service` → `server.port=8081`
* `product-service` → `server.port=8082`
* `payment-service` → `server.port=8083`
* `order-service` → `server.port=8084`
* `eureka-server` → `server.port=8761`

## 5. ENABLE EUREKA
* `eureka-server` uses `@EnableEurekaServer` in the main class.
* All other services use `@EnableDiscoveryClient` and configure the Eureka URL: `eureka.client.service-url.defaultZone=http://localhost:8761/eureka`.

---

## 6. TASK 7.1 – LIBRARY MICROSERVICES DESIGN
**Design:**
* **Book Service:** Responsible strictly for managing book inventory (adding, updating, removing books).
* **User Service:** Responsible for managing library users (registration, profiles, authentication).
* **Borrow Service:** Responsible for managing the lending process (checking out and returning books, tracking due dates).
* **Notification Service:** Responsible for sending alerts (due date reminders, overdue warnings, availability notifications).

## 7. TASK 7.2 – API COMMUNICATION
Implemented between `Order Service` and `Payment Service`.
**Steps:**
1. The user calls the `/orders/pay` API on the Order Service.
2. Order Service uses `RestTemplate` to call `http://PAYMENT-SERVICE/pay`.
3. Payment Service processes the dummy payment request.
4. Response is returned to Order Service and then to the user.
*(See `OrderController.java` and `PaymentController.java` for the code).*

## 8. TASK 7.3 – DECENTRALIZED DATABASE DESIGN
**Design Principle:**
In a true microservices architecture, each service must have its own isolated database to ensure loose coupling.
* **Patient Service:** Connects purely to its own `Patient DB` (Stores patient demographics, history).
* **Doctor Service:** Connects to the `Doctor DB` (Stores doctor profiles, schedules, specialties).
* **Appointment Service:** Connects to the `Appointment DB` (Stores booking slots, connecting Doctor IDs to Patient IDs).
*(For the current Eclipse workspaces, we use in-memory ArrayLists/Strings to simulate data storage without needing a real DB).*

## 9. TASK 7.4 – SERVICE REGISTRY
All 4 business services automatically register with `eureka-server` upon startup via the `@EnableDiscoveryClient` annotation.
Instead of using hardcoded localhost:8083 URLs, internal communication happens via service names. Example: The Order Service uses the RestTemplate call: `restTemplate.getForObject("http://PAYMENT-SERVICE/pay", String.class);`.

## 10. TASK 7.5 – MONOLITH TO MICROSERVICES
**Splitting a Monolith:**
When migrating an older E-Commerce app to microservices:
* **User Service:** Extracted to purely handle user logins and profiles.
* **Order Service:** Extracted to handle cart and order placements.
**Applied Concepts:**
* **Loose Coupling:** If the Order Service goes offline, the User Service is unaffected.
* **Single Responsibility:** User Service only cares about users; Order Service only about orders.
* **Independent Deployment:** The User Service can be updated and restarted without bringing down the Order Service.

---

## 11. IMPLEMENTATION REQUIREMENTS
Controllers with simple endpoints and in-memory storage have been added:
* `/users` inside User Service (Returns Alice, Bob, Charlie).
* `/products` inside Product Service (Returns Laptop, Smartphone, Tablet).
* `/pay` inside Payment Service (Returns success message).
* `/orders` & `/orders/pay` inside Order Service (Returns mock orders & triggers API call to Payment).

## 12. RUNNING IN ECLIPSE
**Steps:**
1. Right-click on `eureka-server` -> Run As -> Spring Boot App. **Wait for it to fully start.**
2. Right-click on the other 4 services -> Run As -> Spring Boot App (one by one).
3. Open a browser and visit `http://localhost:8761`. You should see the Eureka dashboard with all 4 services registered (USER-SERVICE, PRODUCT-SERVICE, PAYMENT-SERVICE, ORDER-SERVICE).

## 13. TESTING
Use Postman or your web browser:
1. Try accessing `http://localhost:8084/orders/pay`.
2. Notice that the output includes the response from the Payment Service, proving that the Order Service successfully communicated with it using Eureka service discovery.
