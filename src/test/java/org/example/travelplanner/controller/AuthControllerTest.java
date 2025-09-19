package org.example.travelplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.travelplanner.dto.LoginRequestDTO;
import org.example.travelplanner.dto.UserDTO;
import org.example.travelplanner.security.JwUtil;
import org.example.travelplanner.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private JwUtil jwUtil;
    private LoginRequestDTO loginRequest;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequestDTO();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
    }

    @Test
    void login_ShouldReturnJwtToken() throws Exception {
        when(userService.login(anyString(), anyString())).thenReturn(userDTO);
        when(jwUtil.generateToken(anyString())).thenReturn("mocked-jwt-token");
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("mocked-jwt-token"));
    }
}