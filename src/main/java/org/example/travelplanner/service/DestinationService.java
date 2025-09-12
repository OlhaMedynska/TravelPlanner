package org.example.travelplanner.service;

import org.example.travelplanner.dto.DestinationDTO;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.exception.ResourceNotFoundException;
import org.example.travelplanner.repository.DestinationRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;

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

//    zwraca obiekt destination jesli istnieje albo wyjatek jak nie(dla dest+atrakcje)
    public Destination getDestinationEntityById(int id){
        return destinationRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination not found"));
    }

    public Page<DestinationDTO> getAllDestinations(Pageable pageable) {
        return destinationRepository.findAll(pageable)
                .map(this::toDTO);
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
        Destination destination = destinationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Destination not found"));
        destination.setName(dto.getName());
        destination.setCountry(dto.getCountry());
        destination.setDescription(dto.getDescription());
        Destination saved = destinationRepository.save(destination);
        return toDTO(saved);
    }

    public void deleteDestination(int id) {
        destinationRepository.deleteById(id);
    }

    public Page<DestinationDTO> searchDestinationsByName(String name, Pageable pageable) {
        Page<DestinationDTO> results = destinationRepository
                .findByNameContainingIgnoreCase(name, pageable)
                .map(this::toDTO);

        if (results.isEmpty()) {
            System.out.println("No destination found for: " + name);
        }
        return results;
    }

    public Page<DestinationDTO> searchDestinationsByCountry(String country, Pageable pageable) {
        Page<DestinationDTO> results = destinationRepository
                .findByCountryContainingIgnoreCase(country, pageable)
                .map(this::toDTO);

        if (results.isEmpty()) {
            System.out.println("No destination found for: " + country);
        }
        return results;
    }
}
