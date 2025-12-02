package com.switflow.swiftFlow.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    

    private String productCode ;

    private String productName;

    private String status;

    private String dateAdded;
}
