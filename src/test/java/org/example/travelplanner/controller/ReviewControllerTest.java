package org.example.travelplanner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.travelplanner.dto.ReviewDTO;
import org.example.travelplanner.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ReviewService reviewService;

    @Test
    void getAll_ShouldReturnList() throws Exception {
        ReviewDTO review = new ReviewDTO();
        review.setComment("Nice");
        review.setRating(5);
        when(reviewService.getAllReviews()).thenReturn(List.of(review));
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comment").value("Nice"));
    }

    @Test
    void getById_ShouldReturnReview() throws Exception {
        ReviewDTO review = new ReviewDTO();
        review.setComment("Nice");
        review.setRating(5);
        when(reviewService.getReviewById(1)).thenReturn(review);
        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Nice"));
    }

    @Test
    void create_ShouldReturnCreatedReview() throws Exception {
        ReviewDTO dto = new ReviewDTO();
        dto.setComment("Great!");
        dto.setRating(5);
        dto.setUserId(1);
        dto.setAttractionId(1);
        ReviewDTO saved = new ReviewDTO();
        saved.setComment("Great!");
        saved.setRating(5);
        saved.setUserId(1);
        saved.setAttractionId(1);
        when(reviewService.createReview(any(ReviewDTO.class))).thenReturn(saved);
        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Great!"));
    }

    @Test
    void update_ShouldReturnUpdatedReview() throws Exception {
        ReviewDTO dto = new ReviewDTO();
        dto.setComment("Updated");
        dto.setRating(4);
        dto.setUserId(1);
        dto.setAttractionId(1);
        ReviewDTO updated = new ReviewDTO();
        updated.setComment("Updated");
        updated.setRating(4);
        when(reviewService.updateReview(any(Integer.class), any(ReviewDTO.class))).thenReturn(updated);
        mockMvc.perform(put("/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").value("Updated"));
    }

    @Test
    void delete_ShouldReturnOk() throws Exception {
        doNothing().when(reviewService).deleteReview(1);
        mockMvc.perform(delete("/reviews/1"))
                .andExpect(status().isOk());
    }
}
