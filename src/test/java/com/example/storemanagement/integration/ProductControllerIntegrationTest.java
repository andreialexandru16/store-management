package com.example.storemanagement.integration;

import com.example.storemanagement.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/products";
    }

    @Test
    void addAndGetProduct() {
        // tell TestRestTemplate to use admin:secret123 for every call
        TestRestTemplate authRest = restTemplate.withBasicAuth("admin","secret123");

        // create a new product
        Product newProd = new Product(null, "IntTestProd", "Integration test product", new BigDecimal("49.99"));

        // POST /api/products
        ResponseEntity<Product> postResponse =
                authRest.postForEntity(baseUrl(), newProd, Product.class);

        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Product created = postResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();

        // GET /api/products/{id}
        ResponseEntity<Product> getResponse =
                authRest.getForEntity(baseUrl() + "/" + created.getId(), Product.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("IntTestProd");
    }
}

