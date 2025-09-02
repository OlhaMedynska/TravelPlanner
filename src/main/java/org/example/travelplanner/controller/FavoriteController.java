package org.example.travelplanner.controller;

import org.example.travelplanner.entity.Favorite;
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
    public Favorite getById(@PathVariable Long id) {
        return favoriteService.getFavoriteById(id);
    }

    @PostMapping
    public Favorite create(@RequestBody Favorite favorite) {
        return favoriteService.createFavorite(favorite);
    }

    @PutMapping("/{id}")
    public Favorite update(@PathVariable Long id, @RequestBody Favorite favorite) {
        return favoriteService.updateFavorite(id, favorite);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
    }
}
