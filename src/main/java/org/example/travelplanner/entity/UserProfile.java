package org.example.travelplanner.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int age;
    private String bio;

    @OneToOne
    @JoinColumn(name="user_id", unique = true)
    private User user;
}
