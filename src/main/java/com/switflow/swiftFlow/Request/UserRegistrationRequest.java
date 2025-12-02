package com.switflow.swiftFlow.Request;

import com.switflow.swiftFlow.utility.Department;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
    private Department role;
}