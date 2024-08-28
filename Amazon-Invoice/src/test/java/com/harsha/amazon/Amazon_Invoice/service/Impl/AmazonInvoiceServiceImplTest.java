package com.harsha.amazon.Amazon_Invoice.service.Impl;

import com.harsha.amazon.Amazon_Invoice.model.Amazon;
import com.harsha.amazon.Amazon_Invoice.respository.AmazonInvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AmazonInvoiceServiceImplTest {

    @InjectMocks
    private AmazonInvoiceServiceImpl amazonInvoiceServiceImpl;

    @Mock
    private AmazonInvoiceRepository amazonInvoiceRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAmazonProdDetails() {
        Amazon amazon = new Amazon();
        amazon.setProductId("P123");
        amazon.setProductName("Product 1");
        amazon.setQuantity(5);
        amazon.setPrice(500.0);

        when(amazonInvoiceRepository.save(any(Amazon.class))).thenReturn(amazon);

        Amazon createdAmazon = amazonInvoiceServiceImpl.createAmazonProdDetails(amazon);

        assertThat(createdAmazon).isNotNull();
        assertThat(createdAmazon.getTotal()).isEqualTo(2500.0);
        assertThat(createdAmazon.getDiscount()).isEqualTo(75.0);
        assertThat(createdAmazon.getProductInvoice()).isEqualTo(425.0);
    }

    @Test
    public void testUpdateAmazonProdDetails() {
        Amazon existingAmazon = new Amazon();
        existingAmazon.setProductId("P123");
        existingAmazon.setProductName("Old Product");
        existingAmazon.setQuantity(10);
        existingAmazon.setPrice(200.0);

        Amazon updatedAmazon = new Amazon();
        updatedAmazon.setProductName("New Product");
        updatedAmazon.setQuantity(5);
        updatedAmazon.setPrice(500.0);

        when(amazonInvoiceRepository.findById(anyString())).thenReturn(Optional.of(existingAmazon));
        when(amazonInvoiceRepository.save(any(Amazon.class))).thenReturn(existingAmazon);

        Amazon resultAmazon = amazonInvoiceServiceImpl.updateAmazonProdDetails("P123", updatedAmazon);

        assertThat(resultAmazon).isNotNull();
        assertThat(resultAmazon.getProductName()).isEqualTo("New Product");
        assertThat(resultAmazon.getTotal()).isEqualTo(2500.0);
        assertThat(resultAmazon.getDiscount()).isEqualTo(75.0);
        assertThat(resultAmazon.getProductInvoice()).isEqualTo(425.0);
    }

    @Test
    public void testUpdateAmazonProdDetails_ProductNotFound() {
        when(amazonInvoiceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                amazonInvoiceServiceImpl.updateAmazonProdDetails("P123", new Amazon())
        );
    }

    @Test
    public void testDeleteAmazonProdDetails() {
        doNothing().when(amazonInvoiceRepository).deleteById(anyString());

        String result = amazonInvoiceServiceImpl.deleteAmazonProdDetails("P123");

        assertThat(result).isEqualTo("Product Invoice deleted successfully");
        verify(amazonInvoiceRepository, times(1)).deleteById("P123");
    }

    @Test
    public void testGetAmazonProdDetails() {
        Amazon amazon = new Amazon();
        amazon.setProductId("P123");
        amazon.setProductName("Product 1");
        amazon.setQuantity(5);
        amazon.setPrice(500.0);

        when(amazonInvoiceRepository.findById(anyString())).thenReturn(Optional.of(amazon));

        Amazon foundAmazon = amazonInvoiceServiceImpl.getAmazonProdDetails("P123");

        assertThat(foundAmazon).isNotNull();
        assertThat(foundAmazon.getProductId()).isEqualTo("P123");
        assertThat(foundAmazon.getProductName()).isEqualTo("Product 1");
    }

    @Test
    public void testGetAmazonProdDetails_ProductNotFound() {
        when(amazonInvoiceRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                amazonInvoiceServiceImpl.getAmazonProdDetails("P123")
        );
    }
}
