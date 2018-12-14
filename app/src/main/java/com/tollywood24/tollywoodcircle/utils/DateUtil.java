package com.tollywood24.tollywoodcircle.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ca6 on 1/3/18.
 */

public class DateUtil {

    /**
     * method to return current current date
     *
     * @param mDateFormat need to pass date format
     * @return current date
     */
    public static String getCurrentDate(String mDateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(mDateFormat);
        Date date = Calendar.getInstance().getTime();
        return format.format(date);
    }

    /**
     * method to return current datetime as timestamp long value
     *
     * @return timestamp
     */
    public static Long getCurrentTimeStamp() {
        return new Date().getTime();
    }


    /**
     * method to return date Format Change
     *
     * @param mRequiredFormat
     * @param inputFormat
     * @param mDate
     * @return
     */
    public static String dateFormatChange(String mRequiredFormat, String inputFormat, String mDate) {
        String date = null;
        Date newDate = null;
        SimpleDateFormat format = new SimpleDateFormat(inputFormat);

        try {
            newDate = format.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat reqFormat = new SimpleDateFormat(mRequiredFormat);
        date = reqFormat.format(newDate);

        return date;
    }

    public static String timeConversion(int totalSeconds) {

        totalSeconds = totalSeconds / 1000;
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
        int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        return String.format("%2d:%2d:%2d", hours, minutes, seconds);
    }


}


