package org.example.travelplanner.service;

import org.example.travelplanner.Attraction;
import org.example.travelplanner.Review;
import org.example.travelplanner.User;
import org.example.travelplanner.dto.ReviewDTO;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.ReviewRepository;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(int id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public Review createReview(ReviewDTO dto) {
        Review review = new Review();

        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(dto.getAttractionId()).orElseThrow(()->new RuntimeException("Attraction is not found"));

        review.setUser(user);
        review.setAttraction(attraction);

        return reviewRepository.save(review);
    }

    public Review updateReview(int id, ReviewDTO dto) {
        Review review = reviewRepository.findById(id).orElseThrow(()-> new RuntimeException("Review not found"));

        review.setComment(dto.getComment());
        review.setRating(dto.getRating());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(dto.getAttractionId()).orElseThrow(()->new RuntimeException("Attraction is not found"));

        review.setUser(user);
        review.setAttraction(attraction);

        return reviewRepository.save(review);
    }

    public void deleteReview(int id) {
        reviewRepository.deleteById(id);
    }
}
