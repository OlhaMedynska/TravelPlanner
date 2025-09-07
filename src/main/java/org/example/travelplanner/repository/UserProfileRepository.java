package org.example.travelplanner.repository;

import org.example.travelplanner.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserProfileRepository  extends JpaRepository<UserProfile, Integer> {
}
