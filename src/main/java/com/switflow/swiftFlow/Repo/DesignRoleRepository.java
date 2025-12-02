package com.switflow.swiftFlow.Repo;

import com.switflow.swiftFlow.Entity.DesignRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DesignRoleRepository extends JpaRepository<DesignRole, Long> {
    Optional<DesignRole> findByUserId(Long userId);
}