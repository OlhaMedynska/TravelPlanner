package org.example.travelplanner.service;
import org.example.travelplanner.dto.UserProfileDTO;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.entity.UserProfile;
import org.example.travelplanner.repository.UserProfileRepository;
import org.example.travelplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
        class UserProfileServiceTest {
            @Mock
            private UserProfileRepository userProfileRepository;
            @Mock
            private UserRepository userRepository;
            @InjectMocks
            private UserProfileService userProfileService;
            @BeforeEach
            void setUp() {
                MockitoAnnotations.openMocks(this);
            }
            @Test
            void createProfile_shouldSaveProfile_whenUserExists() {

                User user = new User();
                user.setId(1);
                UserProfileDTO dto = new UserProfileDTO();
                dto.setUserId(1);
                dto.setAge(25);
                dto.setBio("Test bio");
                UserProfile savedProfile = new UserProfile();
                savedProfile.setId(10);
                savedProfile.setUser(user);
                savedProfile.setAge(25);
                savedProfile.setBio("Test bio");
                when(userRepository.findById(1)).thenReturn(Optional.of(user));
                when(userProfileRepository.save(any(UserProfile.class))).thenReturn(savedProfile);

                UserProfileDTO result = userProfileService.createProfile(dto);

                assertNotNull(result);
                assertEquals(10, result.getId());
                assertEquals(25, result.getAge());
                assertEquals("Test bio", result.getBio());
                verify(userRepository, times(1)).findById(1);
                verify(userProfileRepository, times(1)).save(any(UserProfile.class));
            }
            @Test
            void getProfileById_shouldReturnProfile_whenExists() {

                User user = new User();
                user.setId(1);
                UserProfile profile = new UserProfile();
                profile.setId(5);
                profile.setUser(user);
                profile.setAge(30);
                profile.setBio("Bio test");
                when(userProfileRepository.findById(5)).thenReturn(Optional.of(profile));

                UserProfileDTO result = userProfileService.getProfileById(5);

                assertNotNull(result);
                assertEquals(5, result.getId());
                assertEquals(30, result.getAge());
                assertEquals("Bio test", result.getBio());
                verify(userProfileRepository, times(1)).findById(5);
            }
            @Test
            void deleteProfile_shouldCallRepository() {

                userProfileService.deleteProfile(99);

                verify(userProfileRepository, times(1)).deleteById(99);
            }
        }