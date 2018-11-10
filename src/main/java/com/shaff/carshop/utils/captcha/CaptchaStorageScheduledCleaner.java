package com.shaff.carshop.utils.captcha;

import com.shaff.carshop.utils.parsers.StringToTimeConverter;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.containers.CaptchaStorage;
import org.apache.log4j.Logger;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CaptchaStorageScheduledCleaner {
    private static final Logger LOG = Logger.getLogger(CaptchaStorageScheduledCleaner.class);
    private CaptchaStorage storage;
    private String timeOut;
    private ScheduledExecutorService executorService;


    public CaptchaStorageScheduledCleaner(CaptchaStorage storage, String timeOut) {
        this.storage = storage;
        this.timeOut = timeOut;
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void clean() {
        long timeDelay = new StringToTimeConverter().getCleanerTimeOut(timeOut);
        executorService.scheduleWithFixedDelay(() -> removeAllOldCaptchaValues(storage), timeDelay, timeDelay, TimeUnit.MILLISECONDS);
    }

    private void removeAllOldCaptchaValues(CaptchaStorage storage) {
        LOG.debug(LoggerMessages.CLEANER_STARTS);
        List<String> expiredValues = markAllExpiredValues();
        for(String currentElement: expiredValues){
            storage.getContainer().remove(currentElement);
        }
        LOG.debug(LoggerMessages.CLEANER_DONE_ITS_WORK);
    }

    private List<String> markAllExpiredValues(){
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, RegistrationCaptcha> currentElement : storage.getContainer().entrySet()) {
            if (checkIfTimeExpired(currentElement.getValue().getTimeExpired())) {
                result.add(currentElement.getKey());
            }
        }
        return result;
    }

    private boolean checkIfTimeExpired(LocalTime expiredTime) {
        //return expiredTime.get(ChronoField.MILLI_OF_SECOND) < Calendar.getInstance().getTimeInMillis();
        return expiredTime.isBefore(LocalTime.now());
    }
}
