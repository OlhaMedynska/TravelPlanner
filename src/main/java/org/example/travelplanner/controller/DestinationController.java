package org.example.travelplanner.controller;

import org.example.travelplanner.dto.DestinationDTO;
import org.example.travelplanner.entity.Destination;
import org.example.travelplanner.service.DestinationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public List<Destination> getAll() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public Destination getById(@PathVariable int id) {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public Destination create(@RequestBody DestinationDTO dto) {
        return destinationService.createDestination(dto);
    }

    @PutMapping("/{id}")
    public Destination update(@PathVariable int id, @RequestBody DestinationDTO dto) {
        return destinationService.updateDestination(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        destinationService.deleteDestination(id);
    }
}
