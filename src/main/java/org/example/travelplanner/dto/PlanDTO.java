package org.example.travelplanner.dto;

import java.time.LocalDate;
import java.util.Set;

public class PlanDTO {
    private LocalDate endDate;
    private LocalDate startDate;
    private int userId;
    private Set<Integer> attractionIds;
    private String name;
    private String comment;


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setAttractionIds(Set<Integer> attractionIds) {
        this.attractionIds = attractionIds;
    }

    public Set<Integer> getAttractionIds() {
        return attractionIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
