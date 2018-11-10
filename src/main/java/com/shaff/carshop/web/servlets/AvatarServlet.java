package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.utils.avatar.AvatarSaver;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@WebServlet(ServletPath.AVATAR)

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AvatarServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AvatarServlet.class);
    private static final String FORMAT_JPG = "jpg";
    private static final String DEFAULT_AVATAR_NAME = "default_avatar.png";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute(RequestParameters.USER);
        String userLogin = user.getLogin() + ".";
        String filePath = (String) getServletContext().getAttribute(ContextAttributes.UPLOADS_PATH);
        File file = new File(filePath + userLogin + FORMAT_JPG);

        BufferedImage image;
        if (file.exists()) {
            image = ImageIO.read(file);
        } else {
            image = ImageIO.read(new File(filePath + DEFAULT_AVATAR_NAME));
        }
        ImageIO.write(image, FORMAT_JPG, resp.getOutputStream());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute(RequestParameters.USER);
        new AvatarSaver().saveImageToConcretePath(req, user.getLogin());
        resp.sendRedirect(ServletPath.HOME);
    }
}
