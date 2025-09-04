package org.example.travelplanner.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private String comment;
    private int rating;
    private int userId;
    private int attractionId;
}
