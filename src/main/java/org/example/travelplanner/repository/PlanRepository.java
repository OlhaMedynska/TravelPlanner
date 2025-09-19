package org.example.travelplanner.repository;

import org.example.travelplanner.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
    List<Plan> findByUserId(int userId);

    @Query("SELECT p FROM Plan p JOIN p.attractions a WHERE a.id = :attractionId")
    List<Plan> findByAttractionId(@Param("attractionId") int attractionId);
}