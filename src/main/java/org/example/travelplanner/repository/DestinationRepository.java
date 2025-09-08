package org.example.travelplanner.repository;

import org.example.travelplanner.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    List<Destination> findByNameContainingIgnoreCase(String name);
    List<Destination> findByCountryContainingIgnoreCase(String country);
}
