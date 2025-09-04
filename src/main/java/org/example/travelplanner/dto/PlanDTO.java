package org.example.travelplanner.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class PlanDTO {
    private LocalDate endDate;
    private LocalDate startDate;
    private int userId;
    private Set<Integer> attractionIds;
    private String name;
    private String comment;
}
