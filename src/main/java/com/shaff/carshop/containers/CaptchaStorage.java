package com.shaff.carshop.containers;

import com.shaff.carshop.utils.captcha.RegistrationCaptcha;

import java.util.HashMap;
import java.util.Map;

public class CaptchaStorage {
    private Map<String, RegistrationCaptcha> container = new HashMap<>();

    public Map<String, RegistrationCaptcha> getContainer() {
        return container;
    }

    public void setContainer(Map<String, RegistrationCaptcha> container) {
        this.container = container;
    }
}
