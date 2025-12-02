package com.switflow.swiftFlow.Repo;

import com.switflow.swiftFlow.Entity.InspectionRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InspectionRoleRepository extends JpaRepository<InspectionRole, Long> {
    Optional<InspectionRole> findByUserId(Long userId);
}