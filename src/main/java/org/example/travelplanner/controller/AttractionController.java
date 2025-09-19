package org.example.travelplanner.controller;

import jakarta.validation.Valid;
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
    public List<AttractionDTO> getAll() {
        return attractionService.getAllAttractions();
    }

    @GetMapping("/{id}")
    public AttractionDTO getById(@PathVariable int id) {
        return attractionService.getAttractionById(id);
    }

    @PostMapping
    public AttractionDTO create(@RequestBody @Valid AttractionDTO dto) {
        return attractionService.createAttraction(dto);
    }

    @PutMapping("/{id}")
    public AttractionDTO update(@PathVariable int id, @RequestBody @Valid AttractionDTO dto) {
        return attractionService.updateAttraction(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        attractionService.deleteAttraction(id);
    }
}