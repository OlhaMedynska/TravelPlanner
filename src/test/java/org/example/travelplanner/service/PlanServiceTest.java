package org.example.travelplanner.service;

import org.example.travelplanner.dto.PlanDTO;
import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Plan;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.PlanRepository;
import org.example.travelplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanServiceTest {
    private PlanRepository planRepository;
    private UserRepository userRepository;
    private AttractionRepository attractionRepository;
    private PlanService planService;

    @BeforeEach
    void setUp() {
        planRepository = mock(PlanRepository.class);
        userRepository = mock(UserRepository.class);
        attractionRepository = mock(AttractionRepository.class);
        planService = new PlanService(planRepository, userRepository, attractionRepository);
    }

    @Test
    void createPlan_ShouldSavePlan() {
        User user = new User();
        user.setId(1);
        PlanDTO dto = new PlanDTO();
        dto.setName("Trip");
        dto.setStartDate(LocalDate.of(2025, 10, 1));
        dto.setEndDate(LocalDate.of(2025, 10, 5));
        dto.setUserId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(planRepository.save(any(Plan.class))).thenAnswer(i -> i.getArgument(0));
        PlanDTO saved = planService.createPlan(dto);
        assertEquals("Trip", saved.getName());
        assertEquals(1, saved.getUserId());
    }

    @Test
    void createPlan_ShouldThrow_WhenStartAfterEnd() {
        PlanDTO dto = new PlanDTO();
        dto.setStartDate(LocalDate.of(2025, 10, 5));
        dto.setEndDate(LocalDate.of(2025, 10, 1));
        Exception ex = assertThrows(RuntimeException.class, () -> planService.createPlan(dto));
        assertEquals("Start date cannot be after end date", ex.getMessage());
    }

    @Test
    void updatePlan_ShouldUpdateExisting() {
        User user = new User();
        user.setId(1);
        Attraction attraction = new Attraction();
        attraction.setId(2);
        Plan existing = new Plan();
        existing.setName("Old Plan");
        existing.setStartDate(LocalDate.of(2025, 9, 1));
        existing.setEndDate(LocalDate.of(2025, 9, 5));
        existing.setUser(user);
        PlanDTO dto = new PlanDTO();
        dto.setName("Updated Plan");
        dto.setStartDate(LocalDate.of(2025, 10, 1));
        dto.setEndDate(LocalDate.of(2025, 10, 5));
        dto.setUserId(1);
        dto.setAttractionIds(Set.of(2));
        when(planRepository.findById(1)).thenReturn(Optional.of(existing));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(attractionRepository.findById(2)).thenReturn(Optional.of(attraction));
        when(planRepository.save(any(Plan.class))).thenAnswer(i -> i.getArgument(0));
        PlanDTO updated = planService.updatePlan(1, dto);
        assertEquals("Updated Plan", updated.getName());
        assertEquals(1, updated.getUserId());
        assertTrue(updated.getAttractionIds().contains(2));
    }

    @Test
    void deletePlan_ShouldCallRepository() {
        doNothing().when(planRepository).deleteById(1);
        planService.deletePlan(1);
        verify(planRepository, times(1)).deleteById(1);
    }
}
