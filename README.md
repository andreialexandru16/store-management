# Store Management API

*Spring Boot · Java 21 · Maven · H2 · JPA · Swagger · Kafka · Basic Auth · Tests · Postman*

---

##  1. Project Setup
- Maven-based Spring Boot application  
- Java 17  
- Directory layout:  
src/
main/
java/com/example/storemanagement
resources/
application.properties
test/

![image](https://github.com/user-attachments/assets/a9d5fb7c-9716-493e-b684-f5337f2b8c0c)

##  2. Domain & Persistence
- **Entity:** `Product` (id, name, description, price)  
- **JPA/Hibernate:** `spring-boot-starter-data-jpa`  
- **Database:** H2 in-memory  
- **Place for screenshot:** H2 console showing `PRODUCTS` table
![image](https://github.com/user-attachments/assets/f4b6300d-18c1-4cb8-b845-08974b8d54ae)
![image](https://github.com/user-attachments/assets/3231cdb6-8b17-4b4f-8f36-c985de72d3ee)

##  3. Security
- **Basic HTTP Auth** with in-memory user “admin”/“secret123”  
- Role-based: only `ROLE_ADMIN` can access `/api/**`  
- Open access to H2-console & Swagger UI  
![image](https://github.com/user-attachments/assets/35c05b74-4199-45f0-99e1-9d6203a8f214)

##  4. REST Endpoints
- **POST** `/api/products` → add a product  
- **GET**  `/api/products/{id}` → find by id  
- **PUT**  `/api/products/{id}/price?newPrice=…` → update price  
- **Error handling:** global `@GlobalExceptionHandler` → `ErrorResponse`

  ##  5. Documentation
- **Swagger/OpenAPI** (`springdoc-openapi`)  
- UI at `http://localhost:8080/swagger-ui.html`  
![image](https://github.com/user-attachments/assets/1d3ea3d5-d708-4aec-91b2-4ee748cf90db)

##  6. Testing
- **Integration test:** `ProductControllerIntegrationTest` with `TestRestTemplate` + Basic Auth  
![image](https://github.com/user-attachments/assets/4f1103fc-1378-4bd1-9c09-6f466dd36379)

##  7. Kafka 
- **Docker containers** for Zookeeper & Kafka  
- **Producer:** on product creation publish `ProductCreatedEvent` to `product-created` topic
- **For cmd**:
- docker run -d --name zookeeper -p 2181:2181 zookeeper:3.6  - for zookeeper
- docker run -d --name kafka -p 9092:9092 -e KAFKA_ZOOKEEPER_CONNECT=host.docker.internal:2181 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 wurstmeister/kafka -  for kafka
![image](https://github.com/user-attachments/assets/cb9e669e-7943-43cf-95b9-73cd79c75bd3)
![image](https://github.com/user-attachments/assets/cffdbdb7-a563-4a2d-833d-8ed0b8c648da)






