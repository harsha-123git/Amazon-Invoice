package com.harsha.amazon.Amazon_Invoice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsha.amazon.Amazon_Invoice.model.Amazon;
import com.harsha.amazon.Amazon_Invoice.service.AmazonInvoiceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AmazonInvoiceController.class)
public class AmazonInvoiceControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmazonInvoiceService amazonInvoiceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateProduct() throws Exception {
        Amazon amazon = new Amazon();
        amazon.setProductId("P123");
        amazon.setProductName("Product 1");
        amazon.setQuantity(2);
        amazon.setPrice(1000.0);

        when(amazonInvoiceService.createAmazonProdDetails(any(Amazon.class))).thenReturn(amazon);

        mockMvc.perform(post("/api/amazon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amazon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("P123"))
                .andExpect(jsonPath("$.productName").value("Product 1"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Amazon amazon = new Amazon();
        amazon.setProductId("P123");
        amazon.setProductName("Updated Product");
        amazon.setQuantity(2);
        amazon.setPrice(1000.0);

        when(amazonInvoiceService.updateAmazonProdDetails(anyString(), any(Amazon.class))).thenReturn(amazon);

        mockMvc.perform(put("/api/amazon/P123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amazon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Updated Product"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(amazonInvoiceService).deleteAmazonProdDetails(anyString());

        mockMvc.perform(delete("/api/amazon/P123"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductById() throws Exception {
        Amazon amazon = new Amazon();
        amazon.setProductId("P123");
        amazon.setProductName("Product 1");
        amazon.setQuantity(2);
        amazon.setPrice(1000.0);

        when(amazonInvoiceService.getAmazonProdDetails(anyString())).thenReturn(amazon);

        mockMvc.perform(get("/api/amazon/P123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("P123"))
                .andExpect(jsonPath("$.productName").value("Product 1"));
    }

    @Test
    public void testGetProducts() throws Exception {
        Amazon amazon = new Amazon();
        amazon.setProductId("P123");
        amazon.setProductName("Product 1");
        amazon.setQuantity(2);
        amazon.setPrice(1000.0);

        List<Amazon> amazonList = Collections.singletonList(amazon);

        when(amazonInvoiceService.getAllAmazonProdDetails()).thenReturn(amazonList);

        mockMvc.perform(get("/api/amazon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value("P123"))
                .andExpect(jsonPath("$[0].productName").value("Product 1"));
    }
}
