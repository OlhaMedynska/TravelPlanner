package org.example.travelplanner.service;

import org.example.travelplanner.dto.DestinationDTO;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    private DestinationDTO toDTO(Destination destination) {
        DestinationDTO dto = new DestinationDTO();
        dto.setName(destination.getName());
        dto.setDescription(destination.getDescription());
        dto.setCountry(destination.getCountry());
        return dto;
    }

    private Destination toEntity(DestinationDTO destinationDTO) {
        Destination destination = new Destination();
        destination.setName(destinationDTO.getName());
        destination.setDescription(destinationDTO.getDescription());
        destination.setCountry(destinationDTO.getCountry());
        return destination;
    }

    public List<DestinationDTO> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO getDestinationById(int id) {
        return destinationRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public DestinationDTO createDestination(DestinationDTO dto) {
        Destination saved = destinationRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public DestinationDTO updateDestination(int id, DestinationDTO dto) {
        Destination destination = destinationRepository.findById(id).orElseThrow(()->new RuntimeException("Destination not found"));
        destination.setName(dto.getName());
        destination.setCountry(dto.getCountry());
        destination.setDescription(dto.getDescription());
        Destination saved = destinationRepository.save(destination);
        return toDTO(saved);
    }

    public void deleteDestination(int id) {
        destinationRepository.deleteById(id);
    }
}
