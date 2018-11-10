package com.shaff.carshop.containers;

import com.shaff.carshop.utils.captcha.strategies.CookiesCaptchaStrategy;
import com.shaff.carshop.utils.captcha.strategies.HiddenFieldCaptchaStrategy;
import com.shaff.carshop.utils.captcha.strategies.SessionCaptchaStrategy;
import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;

import java.util.HashMap;
import java.util.Map;

public class CaptchaStrategyStorage {
    private Map<String, CaptchaStorageStrategy> strategies = new HashMap<>();

    {
        strategies.put("session", new SessionCaptchaStrategy());
        strategies.put("cookies", new CookiesCaptchaStrategy());
        strategies.put("hidden", new HiddenFieldCaptchaStrategy());
    }

    public Map<String, CaptchaStorageStrategy> getStrategies() {
        return strategies;
    }

    public void setStrategies(Map<String, CaptchaStorageStrategy> strategies) {
        this.strategies = strategies;
    }
}
