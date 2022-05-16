package com.example.demo.Form;

public class BuyCartForm {
    String password;
    long id;
    int number;

    @Override
    public String toString() {
        return "BuyCartForm{" +
                "password='" + password + '\'' +
                ", id=" + id +
                ", number=" + number +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
