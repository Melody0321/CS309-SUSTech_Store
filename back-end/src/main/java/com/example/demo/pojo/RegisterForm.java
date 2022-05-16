package com.example.demo.pojo;

import javax.persistence.*;

//RegisterController可以用这个实体类接受传入的表单数据
@Entity
@Table(name = "registerForm")
public class RegisterForm {

    String username;
    String password;
    String checkPass;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    //long id;
    int sid;

    String address;
    String email;
    String code;

    public int getSID() {
        return sid;
    }

    public void setSID(int SID) {
        this.sid = SID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
