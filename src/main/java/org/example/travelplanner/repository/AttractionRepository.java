package org.example.travelplanner.repository;

import org.example.travelplanner.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    List<Attraction> findByDestinationId(int destinationId);
}
