package org.example.travelplanner.repository;

import org.example.travelplanner.entity.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    Page<Attraction> findByDestinationId(int destinationId, Pageable pageable);

    List<Attraction> findByCategoryId(int categoryId);
}
