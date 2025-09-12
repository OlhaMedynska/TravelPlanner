package org.example.travelplanner.repository;

import org.example.travelplanner.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
    List<Plan> findByUserId(int userId);
}
