package com.shaff.carshop.utils.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaCreator {

    public CaptchaCreator() {

    }

    public BufferedImage createCaptchaImage(String captcha) {
        int width = 150;
        int height = 50;

        char[] randomChar = captcha.toCharArray();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphicsElement = bufferedImage.createGraphics();
        Font font = new Font("DC Arabic", Font.ITALIC, 18);

        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        GradientPaint gradient = new GradientPaint(1, 5, Color.green, 5, height / 4, Color.black, true);

        graphicsElement.setFont(font);
        graphicsElement.setRenderingHints(hints);
        graphicsElement.setPaint(gradient);
        graphicsElement.fillRect(0, 0, width, height);
        graphicsElement.setColor(new Color(210, 123, 0));
        Random rnd = new Random();
        int x = 0;
        int y = 0;
        for (int i = 0; i < randomChar.length; i++) {
            x += 10 + (Math.abs(rnd.nextInt()) % 15);
            y = 20 + Math.abs(rnd.nextInt()) % 20;
            graphicsElement.drawChars(randomChar, i, 1, x, y);
        }
        graphicsElement.dispose();

        return bufferedImage;
    }
}
