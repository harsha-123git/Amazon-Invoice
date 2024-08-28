package com.harsha.amazon.Amazon_Invoice.respository;

import com.harsha.amazon.Amazon_Invoice.model.Amazon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmazonInvoiceRepository extends JpaRepository<Amazon,String> {
}
