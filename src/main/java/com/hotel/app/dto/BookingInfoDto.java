package com.hotel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class BookingInfoDto {
    private Integer id;
    @NotBlank(message = "Full name is mandatory")
    @Size(min = 10, max = 255, message = "Full name must be between 10 and 255 characters")
    private String fullName;
    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 11, max = 18, message = "Phone number must be between 11 and 18 characters")
    private String phoneNumber;
    @NotBlank(message = "Room is mandatory")
    private String title;
    @NotNull(message = "Arrival date is mandatory")
    private LocalDate arrivalDate;
    @NotNull(message = "Departure date is mandatory")
    private LocalDate departureDate;
    private Integer total_cost;

    public BookingInfoDto() {
    }

    public BookingInfoDto(Integer id, String fullName, String phoneNumber, String roomTitle, LocalDate arrivalDate, LocalDate departureDate, Integer cost) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.title = roomTitle;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.total_cost = cost;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoomTitle() {
        return title;
    }

    public void setRoomTitle(String roomTitle) {
        this.title = roomTitle;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getCost() {
        return total_cost;
    }

    public void setCost(Integer cost) {
        this.total_cost = cost;
    }
}
