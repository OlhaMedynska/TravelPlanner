package org.example.travelplanner.service;

import org.example.travelplanner.dto.UserProfileDTO;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.entity.UserProfile;
import org.example.travelplanner.repository.UserProfileRepository;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    public List<UserProfileDTO> getAllProfiles() {
        return userProfileRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserProfileDTO getProfileById(int id) {
        return userProfileRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public UserProfileDTO createProfile(UserProfileDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));

        UserProfile profile = toEntity(dto);
        profile.setUser(user);

        UserProfile savedProfile = userProfileRepository.save(profile);
        return toDTO(savedProfile);
    }

    public UserProfileDTO updateProfile(int id, UserProfileDTO dto) {
        UserProfile profile = userProfileRepository.findById(id).orElseThrow(()-> new RuntimeException("Profile not found"));

        profile.setAge(dto.getAge());
        profile.setBio(dto.getBio());

        UserProfile updatedProfile = userProfileRepository.save(profile);
        return toDTO(updatedProfile);
    }

    public void deleteProfile(int id) {
        userProfileRepository.deleteById(id);
    }


    private UserProfileDTO toDTO(UserProfile profile) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setAge(profile.getAge());
        dto.setBio(profile.getBio());
        return dto;
    }

    private UserProfile toEntity(UserProfileDTO dto) {
        UserProfile profile = new UserProfile();
        profile.setAge(dto.getAge());
        profile.setBio(dto.getBio());
        return profile;
    }
}
