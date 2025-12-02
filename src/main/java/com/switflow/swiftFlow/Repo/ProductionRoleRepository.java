package com.switflow.swiftFlow.Repo;

import com.switflow.swiftFlow.Entity.ProductionRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductionRoleRepository extends JpaRepository<ProductionRole, Long> {
    Optional<ProductionRole> findByUserId(Long userId);
}