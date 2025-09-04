package org.example.travelplanner.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {
    @Min(value = 1, message = "UserID must be greater than 0")
    private int userId;

    @Min(value = 1, message = "AttractionID must be greater than 0")
    private int attractionId;
}
