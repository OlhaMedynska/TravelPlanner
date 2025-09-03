package org.example.travelplanner.repository;

import org.example.travelplanner.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
