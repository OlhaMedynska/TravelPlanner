package org.example.travelplanner.service;

import org.example.travelplanner.dto.AttractionDTO;
import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Category;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.CategoryRepository;
import org.example.travelplanner.repository.DestinationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AttractionServiceTest {
    @Mock
    private AttractionRepository attractionRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private DestinationService destinationService;
    @Mock
    private DestinationRepository destinationRepository;
    @InjectMocks
    private AttractionService attractionService;
    private Attraction attraction;
    private Destination destination;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId(1);
        category.setName("Museum");
        destination = new Destination();
        destination.setId(1);
        destination.setName("Paris");
        attraction = new Attraction();
        attraction.setId(1);
        attraction.setName("Louvre");
        attraction.setDescription("Famous museum");
        attraction.setPrice(20.0);
        attraction.setCategory(category);
        attraction.setDestination(destination);
    }

    @Test
    void getAttractionsByDestinationId_ShouldReturnList() {
        Destination destination = new Destination();
        destination.setId(1);
        Attraction attraction = new Attraction();
        attraction.setId(1);
        attraction.setName("Louvre");
        attraction.setDestination(destination);
        when(destinationService.getDestinationEntityById(anyInt())).thenReturn(destination);
        when(attractionRepository.findByDestinationId(anyInt(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(attraction)));
        Page<AttractionDTO> result = attractionService.getAttractionsByDestinationId(1, PageRequest.of(0, 10));
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Louvre", result.getContent().get(0).getName());
        verify(destinationService).getDestinationEntityById(anyInt());
        verify(attractionRepository).findByDestinationId(anyInt(), any(Pageable.class));
    }

    @Test
    void createAttraction_ShouldReturnSavedAttraction() {
        AttractionDTO dto = new AttractionDTO();
        dto.setName("Eiffel Tower");
        dto.setDescription("Iconic landmark");
        dto.setPrice(25.0);
        dto.setCategoryId(1);
        dto.setDestinationId(1);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        when(destinationRepository.findById(anyInt())).thenReturn(Optional.of(destination));
        when(attractionRepository.save(any(Attraction.class))).thenAnswer(invocation -> {
            Attraction arg = invocation.getArgument(0);
            arg.setId(2);
            return arg;
        });
        AttractionDTO saved = attractionService.createAttraction(dto);
        assertNotNull(saved);
        assertEquals("Eiffel Tower", saved.getName());
        assertEquals(1, saved.getCategoryId());
        assertEquals(1, saved.getDestinationId());
    }
}