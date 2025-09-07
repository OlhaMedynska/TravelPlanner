package org.example.travelplanner.controller;

import jakarta.validation.Valid;
import org.example.travelplanner.dto.UserProfileDTO;
import org.example.travelplanner.entity.UserProfile;
import org.example.travelplanner.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public List<UserProfileDTO> getUserProfiles() {
        return userProfileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public UserProfileDTO getById(@PathVariable int id) {
        return userProfileService.getProfileById(id);
    }

    @PostMapping
    public UserProfileDTO create(@Valid @RequestBody UserProfileDTO dto) {
        return userProfileService.createProfile(dto);
    }

    @PutMapping("/{id}")
    public UserProfileDTO update(@PathVariable int id, @Valid @RequestBody UserProfileDTO dto) {
        return userProfileService.updateProfile(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        userProfileService.deleteProfile(id);
    }
}
