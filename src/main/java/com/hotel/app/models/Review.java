package com.hotel.app.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "review")
public class Review {
    @Id
    private Integer id;
    @Column("customer_id")
    private Integer customer;
    @Max(value = 5, message = "Rating must be from 1 to 5")
    @Min(value = 1, message = "Rating must be from 1 to 5")
    private Byte rate;
    private String feedback;

    public Review() {
    }

    public Review(Integer id, Integer customer, Byte rate, String feedback) {
        this.id = id;
        this.customer = customer;
        this.rate = rate;
        this.feedback = feedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
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