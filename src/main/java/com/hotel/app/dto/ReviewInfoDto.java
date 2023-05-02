package com.hotel.app.dto;

import jakarta.validation.constraints.*;

public class ReviewInfoDto {
    private Integer id;
    private String fullName;
    @NotBlank(message = "Email is mandatory")
    @Size(min = 7, max = 70, message = "Email must be between 7 and 70 characters")
    private String email;
    @NotNull(message = "Rate is mandatory")
    @Max(value = 5, message = "Rating must be from 1 to 5")
    @Min(value = 1, message = "Rating must be from 1 to 5")
    private Byte rate;
    private String feedback;

    public ReviewInfoDto() {
    }

    public ReviewInfoDto(Integer id, String fullName, String email, Byte rate, String feedback) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.rate = rate;
        this.feedback = feedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getRate() {
        return rate;
    }

    public void setRate(Byte rate) {
        this.rate = rate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

