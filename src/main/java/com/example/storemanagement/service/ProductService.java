package com.example.storemanagement.service;

import com.example.storemanagement.entity.Product;
import com.example.storemanagement.event.ProductCreatedEvent;
import com.example.storemanagement.exception.ProductNotFoundException;
import com.example.storemanagement.exception.ValidationException;
import com.example.storemanagement.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service layer for Product-related operations with Kafka event publishing.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private static final String TOPIC = "product-created";


    public Product addProduct(Product product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new ValidationException("Product name must not be empty");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Product price must be non-negative");
        }
        Product saved = repository.save(product);
        // Publish ProductCreatedEvent to Kafka
        ProductCreatedEvent event = new ProductCreatedEvent(
                saved.getId(), saved.getName(), saved.getPrice()
        );
        kafkaTemplate.send(TOPIC, event);
        return saved;
    }


    public Optional<Product> findProductById(Long id) {
        return repository.findById(id);
    }


    public Product updatePrice(Long id, BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("New price must be non-negative");
        }
        Product existing = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        existing.setPrice(newPrice);
        return repository.save(existing);
    }
}