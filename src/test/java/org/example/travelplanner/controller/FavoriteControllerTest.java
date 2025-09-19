package org.example.travelplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.travelplanner.dto.FavoriteDTO;
import org.example.travelplanner.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FavoriteControllerTest {
    private MockMvc mockMvc;
    @Mock
    private FavoriteService favoriteService;
    @InjectMocks
    private FavoriteController favoriteController;
    private ObjectMapper objectMapper = new ObjectMapper();
    private FavoriteDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favoriteController).build();
        dto = new FavoriteDTO();
        dto.setUserId(1);
        dto.setAttractionId(1);
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        when(favoriteService.getAllFavorites()).thenReturn(List.of(dto));
        mockMvc.perform(get("/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].attractionId").value(1));
    }

    @Test
    void getById_ShouldReturnDTO() throws Exception {
        when(favoriteService.getFavoriteById(1)).thenReturn(dto);
        mockMvc.perform(get("/favorites/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.attractionId").value(1));
    }

    @Test
    void create_ShouldReturnDTO() throws Exception {
        when(favoriteService.createFavorite(any(FavoriteDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.attractionId").value(1));
    }

    @Test
    void update_ShouldReturnDTO() throws Exception {
        when(favoriteService.updateFavorite(eq(1), any(FavoriteDTO.class))).thenReturn(dto);
        mockMvc.perform(put("/favorites/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.attractionId").value(1));
    }

    @Test
    void delete_ShouldReturnOk() throws Exception {
        doNothing().when(favoriteService).deleteFavorite(1);
        mockMvc.perform(delete("/favorites/1"))
                .andExpect(status().isOk());
    }
}