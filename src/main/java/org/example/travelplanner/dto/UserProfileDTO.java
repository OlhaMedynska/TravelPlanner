package org.example.travelplanner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO {
    private int id;

    @Min(value = 0, message = "Age must be greated or equal to 0")
    private int age;

    @Size(max = 500, message = "Bio can have up to 255 characters")
    private String bio;

    //    id usera do kogo przepisuje profil
    private int userId;
}
