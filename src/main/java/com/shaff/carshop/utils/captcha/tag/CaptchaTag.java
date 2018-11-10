package com.shaff.carshop.utils.captcha.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Date;

public class CaptchaTag extends SimpleTagSupport {
    private String captchaId;
    private Date timeOut;

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        String result = "<img src=\"/captcha?captchaId=" + getCaptchaId() + "\" placeholder=\"Enter captcha\">" +
                "\n" +
                "<input type=\"hidden\" name=\"captchaHidden\" value=\"" + getCaptchaId() + "\">";
        out.print(result);
    }
}
