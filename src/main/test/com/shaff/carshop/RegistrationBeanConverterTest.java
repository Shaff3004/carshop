package com.shaff.carshop;

import com.shaff.carshop.converters.RegistrationBeanConverter;
import com.shaff.carshop.converters.populators.UserPopulator;
import com.shaff.carshop.db.beans.UserRegistrationBean;
import com.shaff.carshop.db.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RegistrationBeanConverterTest {
    private static final String NAME_SERHII_VOLODIN = "Serhii Volodin";
    private static final String LOGIN_SERHII_VOLODIN = "Shaff";
    private static final String EMAIL_SERHII_VOLODIN = "qwerty777@mail.ru";
    private static final String PASSWORD_SERHII_VOLODIN = "qwerty777";
    private static final String GENDER_SERHII_VOLODIN = "male";
    private static final String NEWSLETTER_SERHII_VOLODIN = "on";
    @Mock
    private UserRegistrationBean beanMock;
    private RegistrationBeanConverter converter;

    @Before
    public void setUp() {
        setUpBeanMockBehavior();
        addPopulatorsToConverter();
    }

    private void setUpBeanMockBehavior() {
        beanMock = mock(UserRegistrationBean.class);
        when(beanMock.getName()).thenReturn(NAME_SERHII_VOLODIN);
        when(beanMock.getLogin()).thenReturn(LOGIN_SERHII_VOLODIN);
        when(beanMock.getEmail()).thenReturn(EMAIL_SERHII_VOLODIN);
        when(beanMock.getPassword()).thenReturn(PASSWORD_SERHII_VOLODIN);
        when(beanMock.getGender()).thenReturn(GENDER_SERHII_VOLODIN);
        when(beanMock.getNewsletter()).thenReturn(NEWSLETTER_SERHII_VOLODIN);
    }

    private void addPopulatorsToConverter() {
        converter = new RegistrationBeanConverter();
        converter.addPopulator(new UserPopulator());
    }

    @Test
    public void shouldConvertBeanToUserInstanceWhenAllDataIsCorrect() {
        User realUser = converter.convert(beanMock);

        assertEquals(realUser.getName(), beanMock.getName());
        assertEquals(realUser.getLogin(), beanMock.getLogin());
        assertEquals(realUser.getEmail(), beanMock.getEmail());
        assertEquals(realUser.getPassword(), beanMock.getPassword());
    }
}
