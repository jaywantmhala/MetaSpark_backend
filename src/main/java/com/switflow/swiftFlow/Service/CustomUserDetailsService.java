package com.switflow.swiftFlow.Service;

import com.switflow.swiftFlow.Entity.*;
import com.switflow.swiftFlow.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AdminRoleRepository adminRoleRepository;
    
    @Autowired
    private DesignRoleRepository designRoleRepository;
    
    @Autowired
    private ProductionRoleRepository productionRoleRepository;
    
    @Autowired
    private MechanicRoleRepository mechanicRoleRepository;
    
    @Autowired
    private InspectionRoleRepository inspectionRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        // Add authority based on user's department
        if (user.getDepartment() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getDepartment().name()));
        } else {
            // Fallback to checking role-specific tables
            if (adminRoleRepository.findByUserId(user.getId()).isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            if (designRoleRepository.findByUserId(user.getId()).isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_DESIGN"));
            }
            if (productionRoleRepository.findByUserId(user.getId()).isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_PRODUCTION"));
            }
            if (mechanicRoleRepository.findByUserId(user.getId()).isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_MECHANIC"));
            }
            if (inspectionRoleRepository.findByUserId(user.getId()).isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_INSPECTION"));
            }
        }
        
        return authorities;
    }
}