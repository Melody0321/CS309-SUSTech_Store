package com.example.springproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PurchaseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String username;
    private String date;
    private double money;
    private int type;
    private String description;

    //add getter and setter methods here


    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDate() {
        return date;
    }

    public double getMoney() {
        return money;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}