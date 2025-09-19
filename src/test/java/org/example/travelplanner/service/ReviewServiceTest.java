package org.example.travelplanner.service;

import org.example.travelplanner.dto.ReviewDTO;
import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Review;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.ReviewRepository;
import org.example.travelplanner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private AttractionRepository attractionRepository;
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        userRepository = mock(UserRepository.class);
        attractionRepository = mock(AttractionRepository.class);
        reviewService = new ReviewService(reviewRepository, userRepository, attractionRepository);
    }

    @Test
    void createReview_ShouldReturnSavedReview() {
        User user = new User();
        user.setId(1);
        Attraction attraction = new Attraction();
        attraction.setId(1);
        ReviewDTO dto = new ReviewDTO();
        dto.setUserId(1);
        dto.setAttractionId(1);
        dto.setComment("Great place");
        dto.setRating(5);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(attractionRepository.findById(1)).thenReturn(Optional.of(attraction));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));
        ReviewDTO result = reviewService.createReview(dto);
        assertEquals("Great place", result.getComment());
        assertEquals(5, result.getRating());
        assertEquals(1, result.getUserId());
        assertEquals(1, result.getAttractionId());
    }

    @Test
    void createReview_ShouldThrowException_WhenRatingInvalid() {
        ReviewDTO dto = new ReviewDTO();
        dto.setRating(6);
        Exception exception = assertThrows(RuntimeException.class, () -> reviewService.createReview(dto));
        assertEquals("Rating must be between 1 and 5", exception.getMessage());
    }

    @Test
    void updateReview_ShouldUpdateExistingReview() {
        User user = new User();
        user.setId(1);
        Attraction attraction = new Attraction();
        attraction.setId(1);
        Review existing = new Review();
        existing.setComment("Old comment");
        existing.setRating(3);
        existing.setUser(user);
        existing.setAttraction(attraction);
        ReviewDTO dto = new ReviewDTO();
        dto.setComment("Updated");
        dto.setRating(4);
        dto.setUserId(1);
        dto.setAttractionId(1);
        when(reviewRepository.findById(1)).thenReturn(Optional.of(existing));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(attractionRepository.findById(1)).thenReturn(Optional.of(attraction));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));
        ReviewDTO result = reviewService.updateReview(1, dto);
        assertEquals("Updated", result.getComment());
        assertEquals(4, result.getRating());
    }

    @Test
    void deleteReview_ShouldCallRepository() {
        doNothing().when(reviewRepository).deleteById(1);
        reviewService.deleteReview(1);
        verify(reviewRepository, times(1)).deleteById(1);
    }
}