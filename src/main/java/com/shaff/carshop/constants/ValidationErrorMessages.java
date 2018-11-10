package com.shaff.carshop.constants;

public final class ValidationErrorMessages {
    public static final String WRONG_CAPTCHA = "Wrong captcha";
    public static final String LOGIN_EXISTS = "Current login or email already exist";
    public static final String NAME_FIELD_EMPTY = "Field \"name\" cannot be empty";
    public static final String NAME_LENGTH_TOO_SHORT = "Name length cannot be less than 5";
    public static final String NAME_LENGTH_TOO_LONG = "Name length cannot be greater than 40";
    public static final String INCORRECT_NAME_FORMAT = "Incorrect name. Example: [Surname] [Name]";
    public static final String LOGIN_FIELD_EMPTY = "Field \"login\" cannot be empty";
    public static final String LOGIN_LENGTH_TOO_SHORT = "Login length cannot be less than 4";
    public static final String LOGIN_LENGTH_TOO_LONG = "Login length cannot be greater than 16";
    public static final String INCORRECT_LOGIN_FORMAT = "Login must have only a latin alphabet";
    public static final String EMAIL_FIELD_EMPTY = "Field \"email\" cannot be empty";
    public static final String INCORRECT_EMAIL_FORMAT = "Wrong format of email";
    public static final String PASSWORD_FIELD_EMPTY = "Field \"password\" cannot be empty";
    public static final String PASSWORD_LENGTH_TOO_SHORT = "Password length cannot be less than 8";
    public static final String PASSWORD_LENGTH_TOO_LONG = "Password length cannot be greater than 16";
    public static final String INCORRECT_PASSWORD_FORMAT = "Password must have only a latin alphabet and digits";
    public static final String CONFIRMATION_PASSWORD_FIELD_EMPTY = "Confirmation password field cannot be empty";
    public static final String LENGTH_OF_PASSWORDS_DOES_NOT_COINCIDE = "Length of password doesn't coincide with confirm password length";
    public static final String PASSWORDS_ARE_DIFFERENT = "Password confirmation does not match.";
    public static final String CAPTCHA_CODE_EMPTY = "Enter captcha code";
    public static final String CAPTCHA_CODE_INCORRECT_LENGTH = "Captcha code length must be 6";
    public static final String INCORRECT_CAPTCHA_CODE_FORMAT = "Captcha code must have only digits";
    public static final String REGISTRATION_TIMEOUT_EXPIRED = "Registration timeout expired";

    private ValidationErrorMessages(){

    }
}
