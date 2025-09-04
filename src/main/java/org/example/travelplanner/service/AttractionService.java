package org.example.travelplanner.service;

import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Category;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.dto.AttractionDTO;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.CategoryRepository;
import org.example.travelplanner.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final CategoryRepository categoryRepository;
    private final DestinationService destinationService;
    private final DestinationRepository destinationRepository;

    public AttractionService(AttractionRepository attractionRepository, CategoryRepository categoryRepository, DestinationService destinationService, DestinationRepository destinationRepository) {
        this.attractionRepository = attractionRepository;
        this.categoryRepository = categoryRepository;
        this.destinationService = destinationService;
        this.destinationRepository = destinationRepository;
    }

    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }

    public Attraction getAttractionById(int id) {
        return attractionRepository.findById(id).orElse(null);
    }

    public Attraction createAttraction(AttractionDTO dto) {
        Attraction attraction = new Attraction();
        attraction.setName(dto.getName());
        attraction.setDescription(dto.getDescription());
        attraction.setPrice(dto.getPrice());

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElseThrow(()-> new RuntimeException("Destination not found"));

        attraction.setCategory(category);
        attraction.setDestination(destination);

        return attractionRepository.save(attraction);
    }

    public Attraction updateAttraction(int id, AttractionDTO dto) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow();
        attraction.setName(dto.getName());
        attraction.setDescription(dto.getDescription());
        attraction.setPrice(dto.getPrice());

        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElseThrow(()-> new RuntimeException("Destination not found"));

        attraction.setCategory(category);
        attraction.setDestination(destination);
        return attractionRepository.save(attraction);
    }

    public void deleteAttraction(int id) {
        attractionRepository.deleteById(id);
    }
}
