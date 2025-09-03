package org.example.travelplanner.controller;

import org.example.travelplanner.Attraction;
import org.example.travelplanner.dto.AttractionDTO;
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
    public Attraction getById(@PathVariable int id) {
        return attractionService.getAttractionById(id);
    }

    @PostMapping
    public Attraction create(@RequestBody AttractionDTO dto) {
        return attractionService.createAttraction(dto);
    }

    @PutMapping("/{id}")
    public Attraction update(@PathVariable int id, @RequestBody AttractionDTO dto) {
        return attractionService.updateAttraction(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        attractionService.deleteAttraction(id);
    }
}
