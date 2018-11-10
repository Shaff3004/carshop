package com.shaff.carshop;

import com.shaff.carshop.utils.parsers.StringToTimeConverter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertTrue;

public class StringToTimeConverterTest {
    private StringToTimeConverter manager;

    @Before
    public void setUp() {
        manager = new StringToTimeConverter();
    }

    @Test
    public void shouldReturnValueOfMillisecondsGreaterThanExpectedValueWhenMethodWasCalled() {
        String time = "5:00";
        long testValue = manager.getCleanerTimeOut(time);
        long expectedValue = 299990;
        assertTrue(testValue > expectedValue);
    }

    @Test
    public void shouldReturnEqualValueWhenMethodWasCalled() {
        String timeOut = "00:30:00";
        LocalTime testValue = manager.getOperationTimeOut(timeOut);
        LocalTime timeNow = LocalTime.now();
        assertTrue(testValue.isAfter(timeNow));
    }
}
