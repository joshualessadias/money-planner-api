package com.joshuadias.moneyplannerapi.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date addMonthsToDate(Date date, int months) {
        if (months == 0) {
            return date;
        }
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }
}
