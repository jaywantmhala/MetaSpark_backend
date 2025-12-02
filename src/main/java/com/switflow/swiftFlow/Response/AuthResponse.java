package com.switflow.swiftFlow.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.switflow.swiftFlow.Entity.User;
import com.switflow.swiftFlow.utility.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long id;
    private String username;
    
    @JsonProperty("roles")
    private Role role;
    
    public AuthResponse(String token, User user, Role role) {
        this.token = token;
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = role;
    }
}