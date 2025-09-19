package org.example.travelplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.travelplanner.dto.PlanDTO;
import org.example.travelplanner.service.PlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PlanController.class)
class PlanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private PlanService planService;

    @Test
    void getAll_ShouldReturnList() throws Exception {
        PlanDTO dto = new PlanDTO();
        dto.setName("Trip");
        when(planService.getAllPlans()).thenReturn(List.of(dto));
        mockMvc.perform(get("/plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Trip"));
    }

    @Test
    void getById_ShouldReturnPlan() throws Exception {
        PlanDTO dto = new PlanDTO();
        dto.setName("Trip");
        when(planService.getPlanById(1)).thenReturn(dto);
        mockMvc.perform(get("/plans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Trip"));
    }

    @Test
    void create_ShouldReturnSavedPlan() throws Exception {
        PlanDTO dto = new PlanDTO();
        dto.setName("Trip");
        dto.setStartDate(LocalDate.of(2025, 10, 1));
        dto.setEndDate(LocalDate.of(2025, 10, 5));
        dto.setUserId(1);
        dto.setAttractionIds(Set.of(1));

        when(planService.createPlan(any(PlanDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Trip"));
    }

    @Test
    void update_ShouldReturnUpdatedPlan() throws Exception {
        PlanDTO dto = new PlanDTO();
        dto.setName("Updated");
        dto.setStartDate(LocalDate.of(2025, 10, 1));
        dto.setEndDate(LocalDate.of(2025, 10, 5));
        dto.setUserId(1);
        dto.setAttractionIds(Set.of(2));
        when(planService.updatePlan(anyInt(), any(PlanDTO.class))).thenReturn(dto);
        mockMvc.perform(put("/plans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void delete_ShouldReturnOk() throws Exception {
        doNothing().when(planService).deletePlan(1);
        mockMvc.perform(delete("/plans/1"))
                .andExpect(status().isOk());
    }
}