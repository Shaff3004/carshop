package com.shaff.carshop.utils.captcha.strategies;

import com.shaff.carshop.constants.RequestParameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionCaptchaStrategy implements CaptchaStorageStrategy {
    @Override
    public void provideCaptcha(HttpServletRequest req, HttpServletResponse resp, String captchaId) {
        req.getSession().setAttribute(RequestParameters.CAPTCHA_ID, captchaId);
        req.setAttribute(RequestParameters.CAPTCHA_ID, captchaId);
    }

    @Override
    public String getCaptcha(HttpServletRequest req) {
        return req.getSession().getAttribute(RequestParameters.CAPTCHA_ID).toString();
    }
}
