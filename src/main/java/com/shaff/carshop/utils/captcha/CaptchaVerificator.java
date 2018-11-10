package com.shaff.carshop.utils.captcha;

import com.shaff.carshop.constants.ValidationErrorMessages;
import com.shaff.carshop.containers.CaptchaStorage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CaptchaVerificator {

    public CaptchaVerificator(){

    }

    public List<String> verifyCaptcha(CaptchaStorage storage, String beanCaptcha, String captchaId) {
        List<String> errors = new ArrayList<>();
        RegistrationCaptcha captcha = storage.getContainer().get(captchaId);
        checkIfRegistrationTimeoutExpired(captcha, errors);
        checkCaptchaValue(beanCaptcha, captcha.getCaptchaKey(), errors);
        return errors;
    }

    private void checkIfRegistrationTimeoutExpired(RegistrationCaptcha captcha, List<String> errors) {
        if (captcha == null || captcha.getTimeExpired().isBefore(LocalTime.now())) {
            errors.add(ValidationErrorMessages.REGISTRATION_TIMEOUT_EXPIRED);
        }
    }

    private void checkCaptchaValue(String beanCaptcha, String requiredCaptcha, List<String> errors) {
        if (!beanCaptcha.equals(requiredCaptcha)) {
            errors.add(ValidationErrorMessages.WRONG_CAPTCHA);
        }
    }
}
