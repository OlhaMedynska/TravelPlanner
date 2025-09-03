package org.example.travelplanner.repository;

import org.example.travelplanner.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
