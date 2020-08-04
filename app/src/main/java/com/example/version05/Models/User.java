package com.example.version05.Models;

public class User {
private String surname,patronymic,name, email,pass,phone,address,kl,letter;
public User(){}

    public User(String name, String email, String pass, String phone,String address,String kl,String surname,String patronymic,String letter) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.address = address;
        this.kl = kl;
        this.surname = surname;
        this.patronymic = patronymic;
        this.letter = letter;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getKl() {
        return kl;
    }

    public void setKl(String kl) {
        this.kl = kl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
