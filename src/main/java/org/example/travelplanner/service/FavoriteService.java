package org.example.travelplanner.service;

import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Favorite;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.dto.FavoriteDTO;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.FavoriteRepository;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, UserRepository userRepository, AttractionRepository attractionRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.attractionRepository = attractionRepository;
    }

    private FavoriteDTO toDTO(Favorite favorite) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        if(favorite.getUser() != null) {
            favoriteDTO.setUserId(favorite.getUser().getId());
        }

        if(favorite.getAttraction() != null) {
            favoriteDTO.setAttractionId(favorite.getAttraction().getId());
        }
        return favoriteDTO;
    }

    private Favorite toEntity(FavoriteDTO dto) {
        Favorite favorite = new Favorite();
        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(dto.getAttractionId()).orElseThrow(()->new RuntimeException("Attraction is not found"));

        favorite.setUser(user);
        favorite.setAttraction(attraction);
        return favorite;
    }

    public List<FavoriteDTO> getAllFavorites() {
        return favoriteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public FavoriteDTO getFavoriteById(int id) {
        return favoriteRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public FavoriteDTO createFavorite(FavoriteDTO dto) {
        Favorite saved = favoriteRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public FavoriteDTO updateFavorite(int id, FavoriteDTO dto) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(()->new RuntimeException("Favorite not found"));

        favorite.setUser(userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found")));
        favorite.setAttraction(attractionRepository.findById(dto.getAttractionId())
                .orElseThrow(()->new RuntimeException("Attraction is not found")));

        Favorite saved = favoriteRepository.save(favorite);
        return toDTO(saved);
    }

    public void deleteFavorite(int id) {
        favoriteRepository.deleteById(id);
    }
}
