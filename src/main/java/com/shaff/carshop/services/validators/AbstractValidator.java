package com.shaff.carshop.services.validators;

import com.shaff.carshop.constants.ValidationErrorMessages;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractValidator<T> implements Validator<T> {
    private static final String NAME_REREXP = "^[a-zA-Zа-яА-Я-'ёЁ]+[ ]{1}[a-zA-Zа-яА-Я'-ёЁ]+$";
    private static final String LOGIN_REGEXP = "^[a-zA-Z]+$";
    private static final String EMAIL_REGEXP = "^([a-z0-9.-]+)@([a-z0-9.-]+).([a-z.]{2,6})$";
    private static final String PASSWORD_REGEXP = "^[a-zA-Z0-9]+$";
    private static final String CAPTCHA_CODE_REGEXP = "[0-9]+";

    protected void validateName(String name, List<String> errors) {
        if (name == null || name.isEmpty()) {
            errors.add(ValidationErrorMessages.NAME_FIELD_EMPTY);
            return;
        }

        if (name.length() < 5) {
            errors.add(ValidationErrorMessages.NAME_LENGTH_TOO_SHORT);
            return;
        }

        if (name.length() > 40) {
            errors.add(ValidationErrorMessages.NAME_LENGTH_TOO_LONG);
            return;
        }

        if (!checkWithRegexp(NAME_REREXP, name)) {
            errors.add(ValidationErrorMessages.INCORRECT_NAME_FORMAT);
        }
    }

    protected void validateLogin(String login, List<String> errors) {
        if (login == null || login.isEmpty()) {
            errors.add(ValidationErrorMessages.LOGIN_FIELD_EMPTY);
            return;
        }

        if (login.length() < 4) {
            errors.add(ValidationErrorMessages.LOGIN_LENGTH_TOO_SHORT);
            return;
        }

        if (login.length() > 16) {
            errors.add(ValidationErrorMessages.LOGIN_LENGTH_TOO_LONG);
            return;
        }

        if (!checkWithRegexp(LOGIN_REGEXP, login)) {
            errors.add(ValidationErrorMessages.INCORRECT_LOGIN_FORMAT);
        }
    }

    protected void validateEmail(String email, List<String> errors) {
        if (email == null || email.isEmpty()) {
            errors.add(ValidationErrorMessages.EMAIL_FIELD_EMPTY);
            return;
        }

        if (!checkWithRegexp(EMAIL_REGEXP, email)) {
            errors.add(ValidationErrorMessages.INCORRECT_EMAIL_FORMAT);
        }
    }

    protected void validatePassword(String password, List<String> errors) {
        if (password == null || password.isEmpty()) {
            errors.add(ValidationErrorMessages.PASSWORD_FIELD_EMPTY);
            return;
        }

        if (password.length() < 8) {
            errors.add(ValidationErrorMessages.PASSWORD_LENGTH_TOO_SHORT);
            return;
        }

        if (password.length() > 16) {
            errors.add(ValidationErrorMessages.PASSWORD_LENGTH_TOO_LONG);
            return;
        }

        if (!checkWithRegexp(PASSWORD_REGEXP, password)) {
            errors.add(ValidationErrorMessages.INCORRECT_PASSWORD_FORMAT);
        }
    }

    protected void validateConfirmationPassword(String password, String confirmPassword, List<String> errors) {
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            errors.add(ValidationErrorMessages.CONFIRMATION_PASSWORD_FIELD_EMPTY);
            return;
        }

        if (password.length() != confirmPassword.length()) {
            errors.add(ValidationErrorMessages.LENGTH_OF_PASSWORDS_DOES_NOT_COINCIDE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            errors.add(ValidationErrorMessages.PASSWORDS_ARE_DIFFERENT);
        }
    }

    protected void validateCaptcha(String captcha, List<String> errors) {
        if (captcha == null || captcha.isEmpty()) {
            errors.add(ValidationErrorMessages.CAPTCHA_CODE_EMPTY);
            return;
        }

        if (!checkWithRegexp(CAPTCHA_CODE_REGEXP, captcha)) {
            errors.add(ValidationErrorMessages.INCORRECT_CAPTCHA_CODE_FORMAT);
            return;
        }

        if (captcha.length() != 6) {
            errors.add(ValidationErrorMessages.CAPTCHA_CODE_INCORRECT_LENGTH);
        }
    }

    private boolean checkWithRegexp(String regexp, String string) {
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(string);
        return m.matches();
    }
}
