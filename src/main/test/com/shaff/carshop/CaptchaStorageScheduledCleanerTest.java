package com.shaff.carshop;

import com.shaff.carshop.utils.captcha.RegistrationCaptcha;
import com.shaff.carshop.containers.CaptchaStorage;
import com.shaff.carshop.utils.captcha.CaptchaStorageScheduledCleaner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CaptchaStorageScheduledCleanerTest {
    private static final String TIME_OUT = "00:01";
    private static final String KEY_OF_ALIVE_CAPTCHA = "1";
    private static final String KEY_OF_EXPIRED_CAPTCHA = "2";
    private static final int PLUS_MINUTES = 2;
    private static final int MINUS_MINUTES = -500;
    private static final int SLEEP_TIME = 2000;
    private Map<String, RegistrationCaptcha> storageMap;
    @Mock
    private CaptchaStorage storage;
    @InjectMocks
    private CaptchaStorageScheduledCleaner cleaner;

    @Before
    public void setUp() {
        storage = mock(CaptchaStorage.class);
        RegistrationCaptcha aliveCaptcha = mock(RegistrationCaptcha.class);
        RegistrationCaptcha expiredCaptcha = mock(RegistrationCaptcha.class);
        LocalTime timeOfAliveCaptcha = LocalTime.now();
        timeOfAliveCaptcha.plusMinutes(2);

        LocalTime timeOfExpiredCaptcha = LocalTime.now();
        timeOfExpiredCaptcha.minusMinutes(2);
        storageMap = new HashMap<>();
        storageMap.put(KEY_OF_ALIVE_CAPTCHA, aliveCaptcha);
        storageMap.put(KEY_OF_EXPIRED_CAPTCHA, expiredCaptcha);

        when(storage.getContainer()).thenReturn(storageMap);
        when(aliveCaptcha.getTimeExpired()).thenReturn(timeOfAliveCaptcha);
        when(expiredCaptcha.getTimeExpired()).thenReturn(timeOfExpiredCaptcha);

        cleaner = new CaptchaStorageScheduledCleaner(storage, TIME_OUT);
    }

    @Test
    public void shouldRemoveCaptchaWithExpiredDateWhenCleanerMadeHisWork() throws InterruptedException {
        int sizeBefore = storageMap.size();
        new Thread(() -> cleaner.clean()).start();
        Thread.sleep(SLEEP_TIME);
        int sizeAfter = storageMap.size();

        assertFalse(storageMap.containsKey(KEY_OF_EXPIRED_CAPTCHA));
        assertTrue(sizeBefore > sizeAfter);
    }
}
