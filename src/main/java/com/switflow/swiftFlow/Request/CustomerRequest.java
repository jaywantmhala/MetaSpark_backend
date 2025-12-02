package com.switflow.swiftFlow.Request;

import lombok.Data;

@Data
public class CustomerRequest {
    
    private String customerName;

    private String companyName;

    private String customerEmail;

    private String customerPhone;

    private String gstNumber;

    private String primaryAddress;
    
    private String status;
    
    private String dateAdded;

    private String billingAddress;
    private String shippingAddress;
}