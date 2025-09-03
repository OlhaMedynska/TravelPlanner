package org.example.travelplanner.controller;

import org.example.travelplanner.Review;
import org.example.travelplanner.dto.ReviewDTO;
import org.example.travelplanner.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAll() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public Review getById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public Review create(@RequestBody ReviewDTO dto) {
        return reviewService.createReview(dto);
    }

    @PutMapping("/{id}")
    public Review update(@PathVariable int id, @RequestBody ReviewDTO dto) {
        return reviewService.updateReview(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
