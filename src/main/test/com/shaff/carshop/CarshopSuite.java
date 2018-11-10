package com.shaff.carshop;

import com.shaff.carshop.db.TransactionManagerTest;
import com.shaff.carshop.db.dao.impl.UserDaoImplTest;
import com.shaff.carshop.services.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CaptchaCreatorTest.class,
        BeanValidatorParameterizedTest.class,
        RegistrationServletTest.class,
        CookiesCaptchaStrategyTest.class,
        SessionCaptchaStrategyTest.class,
        HiddenFieldCaptchaStrategyTest.class,
        StringToTimeConverterTest.class,
        CaptchaStorageScheduledCleanerTest.class,
        RegistrationBeanConverterTest.class,
        TransactionManagerTest.class,
        UserDaoImplTest.class,
        UserServiceTest.class
})
public class CarshopSuite {

}
