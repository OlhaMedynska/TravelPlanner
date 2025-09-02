package org.example.travelplanner.service;

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

    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public Destination updateDestination(Long id, Destination destinationDetails) {
        Destination destination = destinationRepository.findById(id).orElseThrow();
        destination.setName(destinationDetails.getName());
        destination.setCountry(destinationDetails.getCountry());
        destination.setDescription(destinationDetails.getDescription());
        return destinationRepository.save(destination);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }
}
