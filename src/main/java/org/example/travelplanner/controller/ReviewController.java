package org.example.travelplanner.controller;

import jakarta.validation.Valid;
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
    public List<ReviewDTO> getAll() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ReviewDTO getById(@PathVariable int id) {
        return reviewService.getReviewById(id);
    }

    @PostMapping
    public ReviewDTO create(@RequestBody @Valid ReviewDTO dto) {
        return reviewService.createReview(dto);
    }

    @PutMapping("/{id}")
    public ReviewDTO update(@PathVariable int id, @RequestBody @Valid ReviewDTO dto) {
        return reviewService.updateReview(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
