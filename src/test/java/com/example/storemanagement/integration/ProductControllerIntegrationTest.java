package com.example.storemanagement.integration;

import com.example.storemanagement.entity.Product;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void addAndGetProduct() {
        TestRestTemplate adminRest = restTemplate.withBasicAuth("admin", "secret123");

        ResponseEntity<Product> postResp = adminRest.postForEntity(
                "http://localhost:" + port + "/api/products",
                new Product(null, "Test", "descr", BigDecimal.TEN),
                Product.class
        );
        assertThat(postResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        Long id = postResp.getBody().getId();
        ResponseEntity<Product> getResp = adminRest.getForEntity(
                "http://localhost:" + port + "/api/products/" + id,
                Product.class
        );
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
