# Week 8 Microservices Project

This project demonstrates a complete microservices architecture built with Spring Boot, Spring Cloud (Eureka, Gateway, LoadBalancer), and ActiveMQ messaging.

## Setup Instructions for Eclipse / STS
1. Open Eclipse IDE with Spring Tools (STS) installed.
2. Select `File` -> `Import...`
3. Choose `Maven` -> `Existing Maven Projects`
4. Browse to the directory containing this parent `pom.xml` (`microservices-workspace`).
5. Select all the nested modules (`Discovery-MS`, `User-MS`, `AccountMng-MS`, `ApiGateway-MS`) and click Finish.

## Running the Services
Start the services in the following order:
1. **Discovery-MS** (Eureka Server): Run `DiscoveryMsApplication.java`. It will run on `http://localhost:8761`. Wait for it to start.
2. **User-MS**: Run `UserMsApplication.java`. It will run on `http://localhost:8081`.
3. **AccountMng-MS**: Run `AccountMngMsApplication.java`. It will run on `http://localhost:8082`.
4. **ApiGateway-MS**: Run `ApiGatewayMsApplication.java`. It will run on `http://localhost:8080`.

## Testing the Architecture via Postman
### 1. Eureka Registry
Visit `http://localhost:8761` in your browser. Ensure `USER-MS`, `ACCOUNTMNG-MS`, and `APIGATEWAY-MS` show up under the instances list.

### 2. API Gateway Routing
- **Manual Routing**: Send a `GET` request to `http://localhost:8080/user-service/users/hello`. The gateway will forward this to `USER-MS`.
- **Automatic Routing (Discovery Locator)**: Send a `GET` request to `http://localhost:8080/user-ms/users/hello`. Spring Cloud Gateway automatically routes this based on the service ID.

### 3. Load Balancing & Inter-Service REST Call
- Send a `GET` request to `http://localhost:8080/account-service/accounts/user-info` (or via `ACCOUNTMNG-MS` directly on `http://localhost:8082/accounts/user-info`).
- `ACCOUNTMNG-MS` will use a `@LoadBalanced` `RestTemplate` to call `USER-MS` via its service name (`http://USER-MS/...`).
- Try stopping `USER-MS` to see the fallback message. Provide multiple instances of `USER-MS` (change port to `8083` in VM arguments `-Dserver.port=8083`) to see the LoadBalancer rotate traffic.

### 4. Asynchronous Messaging (JMS with ActiveMQ)
- Send a `GET` request to `http://localhost:8080/user-service/users/send-message/HelloWorld`.
- `USER-MS` sends the message to the ActiveMQ queue.
- Check the console logs of `AccountMng-MS`. You will see `Received message from ActiveMQ: HelloWorld`, proving asynchronous communication.
