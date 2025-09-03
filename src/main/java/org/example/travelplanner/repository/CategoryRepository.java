package org.example.travelplanner.repository;

import org.example.travelplanner.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
