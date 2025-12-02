package com.switflow.swiftFlow.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.switflow.swiftFlow.Entity.Status;

@Repository
public interface StatusRepo extends JpaRepository<Status, Integer>{
    
}
