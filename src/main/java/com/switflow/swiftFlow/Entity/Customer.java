package com.switflow.swiftFlow.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    private String customerName;

    private String companyName;

    private String customerEmail;

    private String customerPhone;

    private String gstNumber;

    private String primaryAddress;

    private String billingAddress;

    private String shippingAddress;

    private String status;

    private String dateAdded;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "customer_orders",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Orders> orders;

}