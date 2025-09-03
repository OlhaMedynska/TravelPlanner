package org.example.travelplanner.dto;

public class FavoriteDTO {
    private int userId;
    private int attractionId;

    public FavoriteDTO() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(int attractionId) {
        this.attractionId = attractionId;
    }
}
