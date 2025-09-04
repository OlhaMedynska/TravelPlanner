package org.example.travelplanner.repository;

import org.example.travelplanner.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
}
