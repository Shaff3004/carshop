package com.shaff.carshop.db.entity;

import com.shaff.carshop.constants.Gender;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private String login;
    private String email;
    private String password;
    private Gender gender;
    private boolean newsletter;
    private int roleId;
    private Timestamp banExpiration;

    public User(){

    }

    public User(int id, String name, String login, String email, String password, Gender gender, boolean newsletter, int roleId, Timestamp banExpiration) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.newsletter = newsletter;
        this.roleId = roleId;
        this.banExpiration = banExpiration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Timestamp getBanExpiration() {
        return banExpiration;
    }

    public void setBanExpiration(Timestamp banExpiration) {
        this.banExpiration = banExpiration;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", newsletter=" + newsletter +
                ", roleId=" + roleId +
                ", banExpiration=" + banExpiration +
                '}';
    }
}
