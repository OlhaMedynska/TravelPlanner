package org.example.travelplanner.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    @NotBlank(message = "Comment is required")
    @Size(max=255, message = "Comment must be at most 255 characters")
    private String comment;

    @Min(value = 1, message = "Rating must be at leat 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;

    @Min(value = 1, message = "UserID must greater than 0")
    private int userId;

    @Min(value = 1, message = "AttractionID must greater than 0")
    private int attractionId;
}
