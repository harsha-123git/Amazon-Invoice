package com.harsha.amazon.Amazon_Invoice.service;

import com.harsha.amazon.Amazon_Invoice.model.Amazon;

import java.util.List;

public interface AmazonInvoiceService
{
  public Amazon createAmazonProdDetails(Amazon amazon);
  public Amazon updateAmazonProdDetails(String productId, Amazon amazon);
  public String deleteAmazonProdDetails(String productId);
  public Amazon getAmazonProdDetails(String productId);
   public List<Amazon> getAllAmazonProdDetails();

}
