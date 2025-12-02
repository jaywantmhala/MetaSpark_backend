package com.switflow.swiftFlow.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.switflow.swiftFlow.Entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
    Optional<Customer> findByCustomerEmail(String customerEmail);
}