package com.shaff.carshop.db.beans;

public class UserRegistrationBean {
    private String name;
    private String login;
    private String email;
    private String password;
    private String confirmationPassword;
    private String gender;
    private String newsletter;
    private String captcha;

    public UserRegistrationBean() {

    }

    public UserRegistrationBean(String name, String login, String email, String password, String confirmationPassword, String gender, String newsletter, String captcha) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.confirmationPassword = confirmationPassword;
        this.gender = gender;
        this.newsletter = newsletter;
        this.captcha = captcha;
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

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        return "UserRegistrationBean{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", newsletter='" + newsletter + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
