package org.example.travelplanner.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.travelplanner.dto.UserProfileDTO;
import org.example.travelplanner.service.UserProfileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserProfileController.class)
class UserProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserProfileService userProfileService;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getAllProfiles() throws Exception {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(1);
        dto.setAge(25);
        dto.setBio("Hello!");
        when(userProfileService.getAllProfiles()).thenReturn(List.of(dto));
        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(25));
    }
    @Test
    void getProfileById_found() throws Exception {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(1);
        dto.setAge(30);
        dto.setBio("Traveler");
        when(userProfileService.getProfileById(1)).thenReturn(dto);
        mockMvc.perform(get("/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bio").value("Traveler"));
    }
    @Test
    void createProfile_success() throws Exception {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUserId(1);
        dto.setAge(28);
        dto.setBio("Explorer");
        when(userProfileService.createProfile(any(UserProfileDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bio").value("Explorer"));
    }
    @Test
    void deleteProfile_success() throws Exception {
        Mockito.doNothing().when(userProfileService).deleteProfile(1);
        mockMvc.perform(delete("/profiles/1"))
                .andExpect(status().isOk());
    }
}