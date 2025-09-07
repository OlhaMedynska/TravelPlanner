package org.example.travelplanner.service;

import org.example.travelplanner.dto.UserProfileDTO;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.entity.UserProfile;
import org.example.travelplanner.repository.UserProfileRepository;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public List<UserProfile> getAllProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile getProfileById(int id) {
        return userProfileRepository.findById(id).orElse(null);
    }

    public UserProfile createProfile(UserProfileDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setAge(dto.getAge());
        profile.setBio(dto.getBio());

        return userProfileRepository.save(profile);
    }

    public UserProfile updateProfile(int id, UserProfileDTO dto) {
        UserProfile profile = userProfileRepository.findById(id).orElseThrow(()-> new RuntimeException("Profile not found"));

        profile.setAge(dto.getAge());
        profile.setBio(dto.getBio());

        return userProfileRepository.save(profile);
    }

    public void deleteProfile(int id) {
        userProfileRepository.deleteById(id);
    }
}
