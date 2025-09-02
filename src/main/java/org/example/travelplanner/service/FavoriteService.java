package org.example.travelplanner.service;

import org.example.travelplanner.entity.Favorite;
import org.example.travelplanner.repository.FavoriteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Favorite getFavoriteById(Long id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public Favorite updateFavorite(Long id, Favorite favoriteDetails) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow();
        favorite.setUser(favoriteDetails.getUser());
        favorite.setAttraction(favoriteDetails.getAttraction());
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }
}
