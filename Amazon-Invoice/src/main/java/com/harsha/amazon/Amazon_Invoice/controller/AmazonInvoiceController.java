package com.harsha.amazon.Amazon_Invoice.controller;

import com.harsha.amazon.Amazon_Invoice.model.Amazon;
import com.harsha.amazon.Amazon_Invoice.service.AmazonInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amazon")
public class AmazonInvoiceController {

    @Autowired
    private AmazonInvoiceService amazonInvoiceService;

    @PostMapping
    public Amazon createProduct(@RequestBody Amazon amazon){
        return amazonInvoiceService.createAmazonProdDetails(amazon);
    }
    @PutMapping("/{productId}")
    public Amazon updateProduct(@PathVariable String productId,@RequestBody Amazon amazon){
        return amazonInvoiceService.updateAmazonProdDetails(productId,amazon);
    }
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId){
        amazonInvoiceService.deleteAmazonProdDetails(productId);
    }
    @GetMapping("/{productId}")
    public Amazon getProductById(@PathVariable String productId){
        return amazonInvoiceService.getAmazonProdDetails(productId);
    }
    @GetMapping
    public List<Amazon> getProducts(){
        return amazonInvoiceService.getAllAmazonProdDetails();
    }
}

