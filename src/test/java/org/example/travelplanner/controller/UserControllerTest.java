package org.example.travelplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.travelplanner.dto.UserDTO;
import org.example.travelplanner.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setId(1);
        user1.setUsername("janek");
        user1.setEmail("janek@example.com");
        UserDTO user2 = new UserDTO();
        user2.setId(2);
        user2.setUsername("kasia");
        user2.setEmail("kasia@example.com");
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("janek"))
                .andExpect(jsonPath("$[1].username").value("kasia"));
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() throws Exception {
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setUsername("janek");
        user.setEmail("janek@example.com");
        when(userService.getUserById(1)).thenReturn(user);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("janek"))
                .andExpect(jsonPath("$.email").value("janek@example.com"));
    }

    @Test
    void createUser_ShouldReturnCreatedUser() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("kasia");
        dto.setPassword("secret");
        dto.setEmail("kasia@example.com");
        UserDTO saved = new UserDTO();
        saved.setId(1);
        saved.setUsername("kasia");
        saved.setEmail("kasia@example.com");
        when(userService.createUser(any(UserDTO.class))).thenReturn(saved);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("kasia"));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("updatedUser");
        dto.setPassword("newPass");
        dto.setEmail("new@example.com");
        UserDTO updated = new UserDTO();
        updated.setId(1);
        updated.setUsername("updatedUser");
        updated.setEmail("new@example.com");
        when(userService.updateUser(eq(1), any(UserDTO.class))).thenReturn(updated);
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("updatedUser"))
                .andExpect(jsonPath("$.email").value("new@example.com"));
    }

    @Test
    void deleteUser_ShouldReturnOk() throws Exception {
        doNothing().when(userService).deleteUser(1);
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }
}