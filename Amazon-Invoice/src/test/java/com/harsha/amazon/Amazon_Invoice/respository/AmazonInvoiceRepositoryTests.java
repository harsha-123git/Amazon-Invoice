package com.harsha.amazon.Amazon_Invoice.respository;

import com.harsha.amazon.Amazon_Invoice.model.Amazon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AmazonInvoiceRepositoryTests {

    @Autowired
    private AmazonInvoiceRepository amazonInvoiceRepository;

    private Amazon amazon;

    @BeforeEach
    public void setUp() {
        amazon = new Amazon();
        amazon.setProductId("P123");
        amazon.setProductName("Product 1");
        amazon.setQuantity(5);
        amazon.setPrice(500.0);
    }

    @AfterEach
    public void tearDown() {
        amazonInvoiceRepository.deleteAll();
    }

    @Test
    public void testSaveAmazonProduct() {
        Amazon savedAmazon = amazonInvoiceRepository.save(amazon);

        assertThat(savedAmazon).isNotNull();
        assertThat(savedAmazon.getProductId()).isEqualTo("P123");
        assertThat(savedAmazon.getProductName()).isEqualTo("Product 1");
    }

    @Test
    public void testFindAmazonProductById() {
        amazonInvoiceRepository.save(amazon);

        Optional<Amazon> foundAmazon = amazonInvoiceRepository.findById("P123");

        assertThat(foundAmazon).isPresent();
        assertThat(foundAmazon.get().getProductId()).isEqualTo("P123");
    }

    @Test
    public void testDeleteAmazonProductById() {
        amazonInvoiceRepository.save(amazon);

        amazonInvoiceRepository.deleteById("P123");

        Optional<Amazon> foundAmazon = amazonInvoiceRepository.findById("P123");

        assertThat(foundAmazon).isNotPresent();
    }

    @Test
    public void testFindAllAmazonProducts() {
        Amazon amazon2 = new Amazon();
        amazon2.setProductId("P124");
        amazon2.setProductName("Product 2");
        amazon2.setQuantity(3);
        amazon2.setPrice(300.0);

        amazonInvoiceRepository.save(amazon);
        amazonInvoiceRepository.save(amazon2);

        assertThat(amazonInvoiceRepository.findAll()).hasSize(2);
    }
}
