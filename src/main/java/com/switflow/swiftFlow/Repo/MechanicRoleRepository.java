package com.switflow.swiftFlow.Repo;

import com.switflow.swiftFlow.Entity.MechanicRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MechanicRoleRepository extends JpaRepository<MechanicRole, Long> {
    Optional<MechanicRole> findByUserId(Long userId);
}