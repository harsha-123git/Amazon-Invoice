package com.harsha.amazon.Amazon_Invoice.service.Impl;

import com.harsha.amazon.Amazon_Invoice.model.Amazon;
import com.harsha.amazon.Amazon_Invoice.respository.AmazonInvoiceRepository;
import com.harsha.amazon.Amazon_Invoice.service.AmazonInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmazonInvoiceServiceImpl implements AmazonInvoiceService {

    @Autowired
    private AmazonInvoiceRepository amazonInvoiceRepository;

    @Override
    public Amazon createAmazonProdDetails(Amazon amazon) {
        calculateAmazonInvoice(amazon);
        return amazonInvoiceRepository.save(amazon);
    }

    @Override
    public Amazon updateAmazonProdDetails(String productId, Amazon amazon) {
        Amazon existingAmazon=amazonInvoiceRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        existingAmazon.setProductName(amazon.getProductName());
        existingAmazon.setPrice(amazon.getPrice());
        calculateAmazonInvoice(existingAmazon);
        return amazonInvoiceRepository.save(existingAmazon);
    }

    @Override
    public String deleteAmazonProdDetails(String productId) {
        amazonInvoiceRepository.deleteById(productId);
        return "Product Invoice deleted successfully";
    }

    @Override
    public Amazon getAmazonProdDetails(String productId) {
        return amazonInvoiceRepository.findById(productId).orElseThrow(()
                -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Amazon> getAllAmazonProdDetails() {
        return amazonInvoiceRepository.findAll();
    }

    private void calculateAmazonInvoice(Amazon amazon){
        int quantity= amazon.getQuantity();
        double price= amazon.getPrice();
        double total=quantity*price;
        double discount=0;
        if(total<2000){
            discount = 0.12 * price;
        }
        else if (total >2000) {
            discount=0.15*price;
        }
       double  productInvoice=price-discount;

        amazon.setTotal(total);
        amazon.setDiscount(discount);
        amazon.setProductInvoice(productInvoice);
    }
}
