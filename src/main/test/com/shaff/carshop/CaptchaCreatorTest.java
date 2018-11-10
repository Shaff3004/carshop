package com.shaff.carshop;

import com.shaff.carshop.utils.captcha.CaptchaCreator;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class CaptchaCreatorTest {
    private static final String CAPTCHA_TEST_CODE = "123456";

    @Test
    public void shouldReturnBufferedImageInstanceWhenMethodWasCalled() throws IOException {
        assertTrue(new CaptchaCreator().createCaptchaImage(CAPTCHA_TEST_CODE) instanceof BufferedImage);
    }
}
