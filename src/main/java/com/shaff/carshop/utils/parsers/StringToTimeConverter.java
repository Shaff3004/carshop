package com.shaff.carshop.utils.parsers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class StringToTimeConverter {

    public LocalTime getOperationTimeOut(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime timeOut = LocalTime.parse(time, formatter);
        return LocalTime.now().plusHours(timeOut.getHour()).plusMinutes(timeOut.getMinute()).plusSeconds(timeOut.getSecond());
    }

    public long getCleanerTimeOut(String time){
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, getMinutes(time));
        calendar.add(Calendar.SECOND, getSeconds(time));
        return calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
    }

    private int getMinutes(String time){
        return Integer.parseInt(time.substring(0, time.indexOf(":")));
    }

    private int getSeconds(String time){
        return Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length()));
    }
}
