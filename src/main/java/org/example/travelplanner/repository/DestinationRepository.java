package org.example.travelplanner.repository;

import org.example.travelplanner.entity.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    Page<Destination> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Destination> findByCountryContainingIgnoreCase(String country, Pageable pageable);
}
