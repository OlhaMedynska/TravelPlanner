package org.example.travelplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.travelplanner.dto.AttractionDTO;
import org.example.travelplanner.service.AttractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AttractionControllerTest {
    @Mock
    private AttractionService attractionService;
    @InjectMocks
    private AttractionController attractionController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(attractionController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAll_ShouldReturnList() throws Exception {
        AttractionDTO dto = new AttractionDTO();
        dto.setName("Louvre");
        when(attractionService.getAllAttractions()).thenReturn(List.of(dto));
        mockMvc.perform(get("/attractions").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Louvre"));
    }

    @Test
    void create_ShouldReturnSavedAttraction() throws Exception {
        AttractionDTO dto = new AttractionDTO();
        dto.setName("Louvre");
        dto.setDescription("Famous museum in Paris");
        dto.setPrice(15.0);
        dto.setCategoryId(1);
        dto.setDestinationId(1);
        when(attractionService.createAttraction(any(AttractionDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/attractions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Louvre"))
                .andExpect(jsonPath("$.price").value(15.0))
                .andExpect(jsonPath("$.description").value("Famous museum in Paris"));
    }

    @Test
    void delete_ShouldCallService() throws Exception {
        mockMvc.perform(delete("/attractions/1"))
                .andExpect(status().isOk());
    }
}
