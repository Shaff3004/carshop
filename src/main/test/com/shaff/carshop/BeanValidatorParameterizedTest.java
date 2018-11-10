package com.shaff.carshop;

import com.shaff.carshop.db.beans.UserRegistrationBean;
import com.shaff.carshop.constants.ValidationErrorMessages;
import com.shaff.carshop.services.validators.UserRegistrationBeanValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BeanValidatorParameterizedTest {
    private UserRegistrationBean bean;
    private String expectedErrorMessage;


    public BeanValidatorParameterizedTest(UserRegistrationBean bean, String expectedErrorMessage) {
        this.bean = bean;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters(name = "{index}:expectedErrors{2}")
    public static Iterable<Object[]> dataForTest(){
        return Arrays.asList(new Object[][]{
                {new UserRegistrationBean("Serhii", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_NAME_FORMAT},
                {new UserRegistrationBean("Se", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.NAME_LENGTH_TOO_SHORT},
                {new UserRegistrationBean("Seasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.NAME_LENGTH_TOO_LONG},
                {new UserRegistrationBean("", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.NAME_FIELD_EMPTY},
                {new UserRegistrationBean("Serhii Volodin", "", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.LOGIN_FIELD_EMPTY},
                {new UserRegistrationBean("Serhii Volodin", "qw", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.LOGIN_LENGTH_TOO_SHORT},
                {new UserRegistrationBean("Serhii Volodin", "qweeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.LOGIN_LENGTH_TOO_LONG},
                {new UserRegistrationBean("Serhii Volodin", "!!!!!!", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_LOGIN_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "1214141", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_LOGIN_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff08", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_LOGIN_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff!", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_LOGIN_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "examplegmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_EMAIL_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_EMAIL_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "asfagagsagasgag",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_EMAIL_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "",
                        "qwerty777", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.EMAIL_FIELD_EMPTY},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.PASSWORD_FIELD_EMPTY},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "as", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.PASSWORD_LENGTH_TOO_SHORT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "asddddddddddddddddddd", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.PASSWORD_LENGTH_TOO_LONG},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "!@#522fafa", "qwerty777", "male", "on", "121212"), ValidationErrorMessages.INCORRECT_PASSWORD_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "qwerty777", "", "male", "on", "121212"), ValidationErrorMessages.CONFIRMATION_PASSWORD_FIELD_EMPTY},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "qwerty777", "asfas", "male", "on", "121212"), ValidationErrorMessages.LENGTH_OF_PASSWORDS_DOES_NOT_COINCIDE},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty888", "male", "on", "121212"), ValidationErrorMessages.PASSWORDS_ARE_DIFFERENT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", ""), ValidationErrorMessages.CAPTCHA_CODE_EMPTY},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "asasf"), ValidationErrorMessages.INCORRECT_CAPTCHA_CODE_FORMAT},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121"), ValidationErrorMessages.CAPTCHA_CODE_INCORRECT_LENGTH},
                {new UserRegistrationBean("Serhii Volodin", "Shaff", "example@gmail.com",
                        "qwerty777", "qwerty777", "male", "on", "121123123123123"), ValidationErrorMessages.CAPTCHA_CODE_INCORRECT_LENGTH}






        });
    }

    @Test
    public void shouldFailAllValidationsWhenDataIsIncorrect(){
        //assertEquals(expectedErrorMessage, new BeanValidator().validateRegistrationBean(bean).get(0));
        Assert.assertEquals(expectedErrorMessage, new UserRegistrationBeanValidator().validate(bean).get(0));
    }
}
