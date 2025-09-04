package org.example.travelplanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DestinationDTO {
    @NotBlank(message = "Destination name is required")
    @Size(max=100, message = "Destination name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Country is required")
    @Size(max=100, message = "Country must be at most 100 characters")
    private String country;

    @Size(max=255, message = "Description must be at most 255 characters")
    private String description;
}
