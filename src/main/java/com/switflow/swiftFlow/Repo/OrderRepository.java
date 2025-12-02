package com.switflow.swiftFlow.Repo;

import com.switflow.swiftFlow.Entity.Orders;
import com.switflow.swiftFlow.utility.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByDepartment(Department department);
}