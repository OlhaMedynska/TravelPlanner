package org.example.travelplanner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int id;

    @NotBlank(message = "Username is required")
    @Size(min=3, max=50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min=6, max=20, message = "Password must be between 6 and 20 characters")
    private String password;

    @NotBlank(message ="Email is required")
    @Email(message = "Email should be valid")
    private String email;

    private Integer age;
    private String bio;
}
