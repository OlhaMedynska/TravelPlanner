package org.example.travelplanner.service;

import org.example.travelplanner.dto.AttractionDTO;
import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Category;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.exception.ResourceNotFoundException;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.CategoryRepository;
import org.example.travelplanner.repository.DestinationRepository;
import org.example.travelplanner.repository.PlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AttractionService {
    private final AttractionRepository attractionRepository;
    private final CategoryRepository categoryRepository;
    private final DestinationService destinationService;
    private final DestinationRepository destinationRepository;
    private final PlanRepository planRepository;

    public AttractionService(AttractionRepository attractionRepository,
                             CategoryRepository categoryRepository,
                             DestinationService destinationService,
                             DestinationRepository destinationRepository,
                             PlanRepository planRepository) {
        this.attractionRepository = attractionRepository;
        this.categoryRepository = categoryRepository;
        this.destinationService = destinationService;
        this.destinationRepository = destinationRepository;
        this.planRepository = planRepository;
    }

    private Attraction toEntity(AttractionDTO dto) {
        Attraction attraction = new Attraction();
        attraction.setName(dto.getName());
        attraction.setDescription(dto.getDescription());
        attraction.setPrice(dto.getPrice());
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Destination destination = destinationRepository.findById(dto.getDestinationId())
                .orElseThrow(() -> new ResourceNotFoundException("Destination not found"));
        attraction.setCategory(category);
        attraction.setDestination(destination);
        return attraction;
    }

    private AttractionDTO toDTO(Attraction attraction) {
        AttractionDTO dto = new AttractionDTO();
        dto.setName(attraction.getName());
        dto.setDescription(attraction.getDescription());
        dto.setPrice(attraction.getPrice());
        if (attraction.getCategory() != null) dto.setCategoryId(attraction.getCategory().getId());
        if (attraction.getDestination() != null) dto.setDestinationId(attraction.getDestination().getId());
        return dto;
    }

    public List<AttractionDTO> getAllAttractions() {
        return attractionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AttractionDTO getAttractionById(int id) {
        return attractionRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public AttractionDTO createAttraction(AttractionDTO dto) {
        Attraction attraction = toEntity(dto);
        Attraction saved = attractionRepository.save(attraction);
        return toDTO(saved);
    }

    public AttractionDTO updateAttraction(int id, AttractionDTO dto) {
        attractionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attraction not found"));
        Attraction attraction = toEntity(dto);
        attraction.setId(id);
        Attraction saved = attractionRepository.save(attraction);
        return toDTO(saved);
    }

    public void deleteAttraction(int id) {
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attraction not found"));
        if (!planRepository.findByAttractionId(id).isEmpty()) {
            throw new RuntimeException("Cannot delete attraction used in plans");
        }
        attractionRepository.delete(attraction);
    }

    public Page<AttractionDTO> getAttractionsByDestinationId(int destinationId, Pageable pageable) {
        destinationService.getDestinationEntityById(destinationId);
        return attractionRepository.findByDestinationId(destinationId, pageable)
                .map(this::toDTO);
    }
}