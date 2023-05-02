package com.hotel.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomInfoDto {
    private Integer id;
    @NotBlank
    private String type_title;
    @NotNull
    private Integer room_number;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String image_path;
    @NotNull
    private Integer price;
    @NotBlank
    private Boolean room_status;

    public RoomInfoDto() {
    }

    public RoomInfoDto(Integer id, String type_title, Integer room_number, String title, String description, String image_path, Integer price, Boolean room_status) {
        this.id = id;
        this.type_title = type_title;
        this.room_number = room_number;
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.price = price;
        this.room_status = room_status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public Integer getRoom_number() {
        return room_number;
    }

    public void setRoom_number(Integer room_number) {
        this.room_number = room_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getRoom_status() {
        return room_status;
    }

    public void setRoom_status(Boolean room_status) {
        this.room_status = room_status;
    }
}
