package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private int sid;
    //private long id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String address;
    @NotNull
    private String email;
    @NotNull
    private int credit=100;
    @NotNull
    private int balance=0;
    @NotNull
    private String salt;

    private String headshot;

    public int getSID() {
        return sid;
    }

    public void setSID(int SID) {
        this.sid = SID;
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public User(){};

    public User(String username,String password,int sid,String email,String address){
        this.username = username;
        this.password = password;
        this.sid=sid;
        this.email = email;
        this.address=address;
    };
    public User( String username, String password,int sid, String email, int credit, int balance, String address) {

        this.username = username;
        this.password = password;
        this.sid=sid;
        this.email = email;
        this.credit = credit;
        this.balance = balance;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "sid=" + sid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", credit=" + credit +
                ", balance=" + balance +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
        if(sid==user.sid)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

