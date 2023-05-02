package com.hotel.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "booking")
public class Booking {
    @Id
    private Integer id;
    @Column("customer_id")
    private Integer customer;
    @Column("room_id")
    private Integer room;
    @Column("arrival_date")
    private LocalDate arrival;
    @Column("departure_date")
    private LocalDate departure;
    @Column("total_cost")
    private Integer cost;

    public Booking() {
    }

    public Booking(Integer id, Integer customer, Integer room, LocalDate arrival, LocalDate departure, Integer cost) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.arrival = arrival;
        this.departure = departure;
        this.cost = cost;
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

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}