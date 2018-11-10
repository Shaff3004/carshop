package com.shaff.carshop.utils.captcha.strategies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HiddenFieldCaptchaStrategy implements CaptchaStorageStrategy {
    @Override
    public void provideCaptcha(HttpServletRequest req, HttpServletResponse resp, String captchaId) {
        req.setAttribute("hiddenId", captchaId);
    }

    @Override
    public String getCaptcha(HttpServletRequest req) {
        return req.getParameter("captchaHidden");
    }
}
