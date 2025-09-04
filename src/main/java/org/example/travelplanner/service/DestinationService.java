package org.example.travelplanner.service;

import org.example.travelplanner.dto.DestinationDTO;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination getDestinationById(int id) {
        return destinationRepository.findById(id).orElse(null);
    }

    public Destination createDestination(DestinationDTO dto) {
        Destination destination = new Destination();
        destination.setName(dto.getName());
        destination.setDescription(dto.getDescription());
        destination.setCountry(dto.getCountry());
        return destinationRepository.save(destination);
    }

    public Destination updateDestination(int id, DestinationDTO dto) {
        Destination destination = destinationRepository.findById(id).orElseThrow(()->new RuntimeException("Destination not found"));
        destination.setName(dto.getName());
        destination.setCountry(dto.getCountry());
        destination.setDescription(dto.getDescription());
        return destinationRepository.save(destination);
    }

    public void deleteDestination(int id) {
        destinationRepository.deleteById(id);
    }
}
