package com.shaff.carshop.utils.avatar;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AvatarSaver {
    private static final String JPG_FORMAT = ".jpg";
    private static final Logger LOG = Logger.getLogger(AvatarSaver.class);


    public void saveImageToConcretePath(HttpServletRequest request, String userLogin) throws IOException, ServletException {
        String filePath = (String) request.getServletContext().getAttribute(ContextAttributes.UPLOADS_PATH);
        Part avatar = request.getPart(RequestParameters.AVATAR);
        if (avatar.getSize() == 0) {
            return;
        }
        try (OutputStream out = new FileOutputStream(new File(filePath + userLogin + JPG_FORMAT))) {
            InputStream fileContent = avatar.getInputStream();
            int read;
            byte[] bytes = new byte[1024];
            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            LOG.error(LoggerMessages.CANNOT_UPLOAD_AVATAR, e);
        }
    }
}
