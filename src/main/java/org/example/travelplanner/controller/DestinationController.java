package org.example.travelplanner.controller;

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
    public Destination getById(@PathVariable Long id) {
        return destinationService.getDestinationById(id);
    }

    @PostMapping
    public Destination create(@RequestBody Destination destination) {
        return destinationService.createDestination(destination);
    }

    @PutMapping("/{id}")
    public Destination update(@PathVariable Long id, @RequestBody Destination destination) {
        return destinationService.updateDestination(id, destination);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        destinationService.deleteDestination(id);
    }
}
