package org.example.travelplanner.service;
import org.example.travelplanner.dto.FavoriteDTO;
import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Favorite;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.FavoriteRepository;
import org.example.travelplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class FavoriteServiceTest {
    @Mock
    private FavoriteRepository favoriteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AttractionRepository attractionRepository;
    @InjectMocks
    private FavoriteService favoriteService;
    private User user;
    private Attraction attraction;
    private Favorite favorite;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        attraction = new Attraction();
        attraction.setId(1);
        favorite = new Favorite();
        favorite.setUser(user);
        favorite.setAttraction(attraction);
    }
    @Test
    void getAllFavorites_ShouldReturnList() {
        when(favoriteRepository.findAll()).thenReturn(List.of(favorite));
        List<FavoriteDTO> result = favoriteService.getAllFavorites();
        assertEquals(1, result.size());
    }
    @Test
    void getFavoriteById_ShouldReturnDTO() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.of(favorite));
        FavoriteDTO result = favoriteService.getFavoriteById(1);
        assertEquals(1, result.getUserId());
        assertEquals(1, result.getAttractionId());
    }
    @Test
    void createFavorite_ShouldReturnSaved() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(attractionRepository.findById(1)).thenReturn(Optional.of(attraction));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);
        FavoriteDTO dto = new FavoriteDTO();
        dto.setUserId(1);
        dto.setAttractionId(1);
        FavoriteDTO result = favoriteService.createFavorite(dto);
        assertEquals(1, result.getUserId());
        assertEquals(1, result.getAttractionId());
    }
    @Test
    void updateFavorite_ShouldReturnUpdated() {
        when(favoriteRepository.findById(1)).thenReturn(Optional.of(favorite));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(attractionRepository.findById(1)).thenReturn(Optional.of(attraction));
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);
        FavoriteDTO dto = new FavoriteDTO();
        dto.setUserId(1);
        dto.setAttractionId(1);
        FavoriteDTO result = favoriteService.updateFavorite(1, dto);
        assertEquals(1, result.getUserId());
        assertEquals(1, result.getAttractionId());
    }
    @Test
    void deleteFavorite_ShouldCallRepository() {
        favoriteService.deleteFavorite(1);
        verify(favoriteRepository, times(1)).deleteById(1);
    }
}