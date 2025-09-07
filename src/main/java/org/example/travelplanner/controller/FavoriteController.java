package org.example.travelplanner.controller;

import jakarta.validation.Valid;
import org.example.travelplanner.entity.Favorite;
import org.example.travelplanner.dto.FavoriteDTO;
import org.example.travelplanner.service.FavoriteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public List<FavoriteDTO> getAll() {
        return favoriteService.getAllFavorites();
    }

    @GetMapping("/{id}")
    public FavoriteDTO getById(@PathVariable int id) {
        return favoriteService.getFavoriteById(id);
    }

    @PostMapping
    public FavoriteDTO create(@RequestBody @Valid FavoriteDTO dto) {
        return favoriteService.createFavorite(dto);
    }

    @PutMapping("/{id}")
    public FavoriteDTO update(@PathVariable int id, @RequestBody @Valid FavoriteDTO dto) {
        return favoriteService.updateFavorite(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        favoriteService.deleteFavorite(id);
    }
}
