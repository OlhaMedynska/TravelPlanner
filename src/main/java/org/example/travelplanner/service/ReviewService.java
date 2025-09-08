package org.example.travelplanner.service;

import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Review;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.dto.ReviewDTO;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.ReviewRepository;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;

    public ReviewService(ReviewRepository reviewRepository,UserRepository userRepository,AttractionRepository attractionRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.attractionRepository = attractionRepository;
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(int id) {
        return reviewRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public ReviewDTO createReview(ReviewDTO dto) {
        validateRating(dto.getRating());
        Review review = toEntity(dto);

        Review savedReview = reviewRepository.save(review);
        return toDTO(savedReview);
    }

    public ReviewDTO updateReview(int id, ReviewDTO dto) {
        validateRating(dto.getRating());
        Review review = reviewRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Review not found"));

        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        Attraction attraction = attractionRepository.findById(dto.getAttractionId()).orElseThrow(()->new RuntimeException("Attraction is not found"));
        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));

        review.setUser(user);
        review.setAttraction(attraction);

        Review savedReview = reviewRepository.save(review);
        return toDTO(savedReview);
    }

    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        if(review.getAttraction() != null) {
            dto.setAttractionId(review.getAttraction().getId());
        }
        if(review.getUser() != null) {
            dto.setUserId(review.getUser().getId());
        }
        return dto;
    }

    private Review toEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(dto.getAttractionId()).orElseThrow(()->new RuntimeException("Attraction not found"));

        review.setUser(user);
        review.setAttraction(attraction);

        return review;
    }

    private void validateRating(int rating) {
        if(rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
    }
}
