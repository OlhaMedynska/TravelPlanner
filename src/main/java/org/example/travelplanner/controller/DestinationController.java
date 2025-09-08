package org.example.travelplanner.controller;

import jakarta.validation.Valid;
import org.example.travelplanner.dto.AttractionDTO;
import org.example.travelplanner.dto.DestinationDTO;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.service.AttractionService;
import org.example.travelplanner.service.DestinationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;
    private final AttractionService attractionService;

    public DestinationController(DestinationService destinationService, AttractionService attractionService) {
        this.destinationService = destinationService;
        this.attractionService = attractionService;
    }

    @GetMapping
    public List<DestinationDTO> getAll() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public DestinationDTO getById(@PathVariable int id) {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public DestinationDTO create(@RequestBody @Valid DestinationDTO dto) {
        return destinationService.createDestination(dto);
    }

    @PutMapping("/{id}")
    public DestinationDTO update(@PathVariable int id, @RequestBody @Valid DestinationDTO dto) {
        return destinationService.updateDestination(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        destinationService.deleteDestination(id);
    }

    @GetMapping("/search")
    public List<DestinationDTO> searchDestinations(@RequestParam String name) {
        if (name == null || name.isEmpty()) {
            return destinationService.getAllDestinations();
        }
        return destinationService.searchDestinationsByName(name);
    }

    @GetMapping("/searchByCountry")
    public List<DestinationDTO> searchByCountry(@RequestParam String country) {
        if (country == null || country.isEmpty()) {
            return destinationService.getAllDestinations();
        }
        return destinationService.searchDestinationsByCountry(country);
    }

    @GetMapping("/{id}/attractions")
    public List<AttractionDTO> getAttractionsByDestination(@PathVariable int id) {
        return attractionService.getAttractionsByDestinationId(id);
    }
}
