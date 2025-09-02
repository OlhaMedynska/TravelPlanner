package org.example.travelplanner.repository;

import org.example.travelplanner.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
