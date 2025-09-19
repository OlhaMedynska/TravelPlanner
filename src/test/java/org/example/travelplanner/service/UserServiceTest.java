package org.example.travelplanner.service;

import org.example.travelplanner.dto.UserDTO;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.entity.UserProfile;
import org.example.travelplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserById_ShouldReturnUserDTO_WhenUserExists() {

        User user = new User();
        user.setId(1);
        user.setUsername("janek");
        user.setEmail("janek@example.com");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDTO result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals("janek", result.getUsername());
        assertEquals("janek@example.com", result.getEmail());
    }

    @Test
    void getUserById_ShouldReturnNull_WhenUserDoesNotExist() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());
        UserDTO result = userService.getUserById(99);
        assertNull(result);
    }

    @Test
    void createUser_ShouldEncodePassword_AndSaveUser() {
        UserDTO dto = new UserDTO();
        dto.setUsername("kasia");
        dto.setPassword("secret");
        dto.setEmail("kasia@example.com");
        User user = new User();
        user.setId(1);
        user.setUsername("kasia");
        user.setPassword("encodedSecret");
        user.setEmail("kasia@example.com");
        when(passwordEncoder.encode("secret")).thenReturn("encodedSecret");
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO result = userService.createUser(dto);
        assertNotNull(result);
        assertEquals("kasia", result.getUsername());
        verify(passwordEncoder).encode("secret");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_ShouldUpdateExistingUser() {
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("oldUser");
        existingUser.setPassword("oldPass");
        existingUser.setEmail("old@example.com");
        UserProfile profile = new UserProfile();
        profile.setAge(20);
        profile.setBio("old bio");
        existingUser.setProfile(profile);
        UserDTO dto = new UserDTO();
        dto.setUsername("newUser");
        dto.setPassword("newPass");
        dto.setEmail("new@example.com");
        dto.setAge(25);
        dto.setBio("new bio");
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);
        UserDTO result = userService.updateUser(1, dto);
        assertEquals("newUser", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        assertEquals(25, result.getAge());
        assertEquals("new bio", result.getBio());
        verify(userRepository).save(existingUser);
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreValid() {
        User user = new User();
        user.setId(1);
        user.setUsername("janek");
        user.setPassword("encodedPass");
        when(userRepository.findByUsername("janek")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("plainPass", "encodedPass")).thenReturn(true);
        UserDTO result = userService.login("janek", "plainPass");
        assertNotNull(result);
        assertEquals("janek", result.getUsername());
    }

    @Test
    void login_ShouldThrowException_WhenPasswordInvalid() {
        User user = new User();
        user.setId(1);
        user.setUsername("janek");
        user.setPassword("encodedPass");
        when(userRepository.findByUsername("janek")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPass", "encodedPass")).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.login("janek", "wrongPass"));
        assertEquals("Invalid password or username", ex.getMessage());
    }

    @Test
    void deleteUser_ShouldCallRepositoryDelete() {
        doNothing().when(userRepository).deleteById(1);
        userService.deleteUser(1);
        verify(userRepository).deleteById(1);
    }
}