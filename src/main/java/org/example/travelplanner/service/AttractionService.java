package org.example.travelplanner.service;

import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.repository.AttractionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;

    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }

    public Attraction getAttractionById(Long id) {
        return attractionRepository.findById(id).orElse(null);
    }

    public Attraction createAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    public Attraction updateAttraction(Long id, Attraction attractionDetails) {
        Attraction attraction = attractionRepository.findById(id).orElseThrow();
        attraction.setName(attractionDetails.getName());
        attraction.setDescription(attractionDetails.getDescription());
        attraction.setPrice(attractionDetails.getPrice());
        attraction.setCategory(attractionDetails.getCategory());
        attraction.setDestination(attractionDetails.getDestination());
        return attractionRepository.save(attraction);
    }

    public void deleteAttraction(Long id) {
        attractionRepository.deleteById(id);
    }
}
