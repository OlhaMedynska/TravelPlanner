package org.example.travelplanner.controller;

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
    public List<Favorite> getAll() {
        return favoriteService.getAllFavorites();
    }

    @GetMapping("/{id}")
    public Favorite getById(@PathVariable int id) {
        return favoriteService.getFavoriteById(id);
    }

    @PostMapping
    public Favorite create(@RequestBody FavoriteDTO dto) {
        return favoriteService.createFavorite(dto);
    }

    @PutMapping("/{id}")
    public Favorite update(@PathVariable int id, @RequestBody FavoriteDTO dto) {
        return favoriteService.updateFavorite(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        favoriteService.deleteFavorite(id);
    }
}
