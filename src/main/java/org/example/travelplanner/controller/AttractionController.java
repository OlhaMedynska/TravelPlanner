package org.example.travelplanner.controller;

import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.service.AttractionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public List<Attraction> getAll() {
        return attractionService.getAllAttractions();
    }

    @GetMapping("/{id}")
    public Attraction getById(@PathVariable Long id) {
        return attractionService.getAttractionById(id);
    }

    @PostMapping
    public Attraction create(@RequestBody Attraction attraction) {
        return attractionService.createAttraction(attraction);
    }

    @PutMapping("/{id}")
    public Attraction update(@PathVariable Long id, @RequestBody Attraction attraction) {
        return attractionService.updateAttraction(id, attraction);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        attractionService.deleteAttraction(id);
    }
}
