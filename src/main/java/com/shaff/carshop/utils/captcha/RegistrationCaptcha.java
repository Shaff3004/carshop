package com.shaff.carshop.utils.captcha;

import com.shaff.carshop.utils.parsers.StringToTimeConverter;
import org.apache.commons.lang3.RandomStringUtils;
import java.awt.image.BufferedImage;
import java.time.LocalTime;

public class RegistrationCaptcha {
    private String captchaKey;
    private LocalTime timeExpired;
    private BufferedImage captchaImage;

    public RegistrationCaptcha(String timeOut) {
        captchaKey = RandomStringUtils.randomNumeric(6);
        timeExpired = new StringToTimeConverter().getOperationTimeOut(timeOut);
        captchaImage = new CaptchaCreator().createCaptchaImage(captchaKey);
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    public LocalTime getTimeExpired() {
        return timeExpired;
    }

    public void setTimeExpired(LocalTime timeExpired) {
        this.timeExpired = timeExpired;
    }

    public BufferedImage getCaptchaImage() {
        return captchaImage;
    }

    public void setCaptchaImage(BufferedImage captchaImage) {
        this.captchaImage = captchaImage;
    }
}
