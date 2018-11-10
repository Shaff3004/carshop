package com.shaff.carshop.db.beans;

public class UserLoginBean {
    private String login;
    private String password;
    private int attempts;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "UserLoginBean{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", attempts=" + attempts +
                '}';
    }
}