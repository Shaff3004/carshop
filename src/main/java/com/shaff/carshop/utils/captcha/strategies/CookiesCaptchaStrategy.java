package com.shaff.carshop.utils.captcha.strategies;

import com.shaff.carshop.constants.RequestParameters;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesCaptchaStrategy implements CaptchaStorageStrategy {
    @Override
    public void provideCaptcha(HttpServletRequest req, HttpServletResponse resp, String captchaId) {
        Cookie cookie = new Cookie(RequestParameters.CAPTCHA_ID, captchaId);
        resp.addCookie(cookie);
        req.setAttribute(RequestParameters.CAPTCHA_ID, captchaId);
    }

    @Override
    public String getCaptcha(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String captchaId = null;
        for (Cookie currentCookie : cookies) {
            if (currentCookie.getName().equals(RequestParameters.CAPTCHA_ID)) {
                captchaId = currentCookie.getValue();
                break;
            }
        }
        return captchaId;
    }
}
