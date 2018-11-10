package com.shaff.carshop.utils.captcha.strategies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaStorageStrategy {
    void provideCaptcha(HttpServletRequest req, HttpServletResponse resp, String captchaId);
    String getCaptcha(HttpServletRequest req);
}
