package com.harsha.amazon.Amazon_Invoice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="Amazon_Invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Amazon {

    @Id
    private String productId;
    private String productName;
    private String productBrand;
    private int quantity;
    private double price;
    private double total;
    private double discount;
    private double productInvoice;
}
