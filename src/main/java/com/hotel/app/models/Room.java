package com.hotel.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "room")
public class Room {
    @Id
    private Integer id;
    @Column("type_id")
    private Integer type;
    @Column("room_number")
    private Integer number;
    private String title;
    private String description;
    @Column("image_path")
    private String image;
    private Integer price;
    @Column("room_status")
    private Boolean status;

    public Room() {
    }

    public Room(Integer id, Integer type, Integer number, String title, String description, String image, Integer price, Boolean status) {
        this.id = id;
        this.type = type;
        this.number = number;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
