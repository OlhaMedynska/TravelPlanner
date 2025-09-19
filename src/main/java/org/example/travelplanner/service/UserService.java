package org.example.travelplanner.service;

import org.example.travelplanner.dto.UserDTO;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.entity.UserProfile;
import org.example.travelplanner.exception.BadRequestException;
import org.example.travelplanner.exception.ConflictException;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        if (user.getProfile() != null) {
            dto.setAge(user.getProfile().getAge());
            dto.setBio(user.getProfile().getBio());
        }
        return dto;
    }

    private User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        user.setEmail(dto.getEmail());

        boolean hasProfileData = dto.getAge() != null || dto.getBio() != null && !dto.getBio().isEmpty();

        if (hasProfileData) {
            UserProfile userProfile = new UserProfile();
            userProfile.setAge(dto.getAge() != null ? dto.getAge() : 0);
            userProfile.setBio(dto.getBio());
            userProfile.setUser(user);
            user.setProfile(userProfile);
        }

        return user;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(int id) {
        return userRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public UserDTO createUser(UserDTO dto) {
        validateUniqueUser(dto);
        User savedUser = userRepository.save(toEntity(dto));
        return toDTO(savedUser);
    }

    public UserDTO updateUser(int id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());

        boolean hasProfileData = (dto.getAge() != null || dto.getBio() != null && !dto.getBio().isEmpty());

        if (user.getProfile() == null && hasProfileData) {
            UserProfile userProfile = new UserProfile();
            userProfile.setAge(dto.getAge() != null ? dto.getAge() : 0);
            userProfile.setBio(dto.getBio());
            userProfile.setUser(user);
            user.setProfile(userProfile);
        } else if (user.getProfile() != null) {
            user.getProfile().setAge(dto.getAge() != null ? dto.getAge() : 0);
            user.getProfile().setBio(dto.getBio());
        }

        return toDTO(userRepository.save(user));
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public UserDTO login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("Invalid username"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }

        return toDTO(user);
    }

    private void validateUniqueUser(UserDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new ConflictException("User name already exists");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Email already exists");
        }
    }
}
