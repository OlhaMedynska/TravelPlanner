package org.example.travelplanner.controller;

import jakarta.validation.Valid;
import org.example.travelplanner.dto.AttractionDTO;
import org.example.travelplanner.dto.DestinationDTO;
import org.example.travelplanner.service.AttractionService;
import org.example.travelplanner.service.DestinationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


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
    public Page<DestinationDTO> getAll(Pageable pageable) {
        return destinationService.getAllDestinations(pageable);
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
    public Page<DestinationDTO> searchDestinations(@RequestParam String name, Pageable pageable) {
        if (name == null || name.isEmpty()) {
            return destinationService.getAllDestinations(Pageable.unpaged());
        }
        return destinationService.searchDestinationsByName(name, pageable);
    }

    @GetMapping("/searchByCountry")
    public Page<DestinationDTO> searchByCountry(@RequestParam String country, Pageable pageable) {
        if (country == null || country.isEmpty()) {
            return destinationService.getAllDestinations(Pageable.unpaged());
        }
        return destinationService.searchDestinationsByCountry(country, pageable);
    }

    @GetMapping("/{id}/attractions")
    public Page<AttractionDTO> getAttractionsByDestination(@PathVariable int id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attractionService.getAttractionsByDestinationId(id, pageable);
    }
}
