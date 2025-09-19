package org.example.travelplanner.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PlanDTO {
    @NotNull(message = "EndDate is required")
    private LocalDate endDate;

    @NotNull(message = "StartDate is required")
    private LocalDate startDate;

    @Min(value = 1, message = "UserID must be greater than 0")
    private int userId;

    @NotEmpty(message = "AttractionIDs cannot be empty")
    private Set<Integer> attractionIds;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Size(max = 255, message = "Comment must be at most 255 characters")
    private String comment;
}
