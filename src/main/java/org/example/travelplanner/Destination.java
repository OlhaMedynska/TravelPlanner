package org.example.travelplanner;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="destinations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;
    private String country;
    private String description;
}
