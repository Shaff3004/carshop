package com.shaff.carshop.web.servlets;

import com.shaff.carshop.utils.captcha.RegistrationCaptcha;
import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.containers.CaptchaStorage;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(ServletPath.CAPTCHA)
public class CaptchaServlet extends HttpServlet {
    private static final String PNG_FORMAT = "png";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        renderImage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        renderImage(req, resp);
    }

    private void renderImage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CaptchaStorageStrategy strategy = (CaptchaStorageStrategy) getServletContext().getAttribute(ContextAttributes.CAPTCHA_STRATEGY);
        String captchaId = strategy.getCaptcha(req);
        CaptchaStorage storage = (CaptchaStorage) getServletContext().getAttribute(ContextAttributes.CAPTCHA_STORAGE);
        RegistrationCaptcha captcha = storage.getContainer().get(captchaId);
        OutputStream os = resp.getOutputStream();
        ImageIO.write(captcha.getCaptchaImage(), PNG_FORMAT, os);
        os.flush();
        os.close();
    }
}
