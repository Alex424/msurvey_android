package com.mad.survey.utils;

import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex on 15/07/15.
 */
public class ConversionUtils {

    public static final String APP_FMT_DATE_ONLY = "yyyy-MM-dd";
    public static final String APP_FMT_TIME_ONLY = "HH:mm";
    public static final String APP_FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String APP_FMT_AIRFMT = "ddMMMyy";
    public static final String APP_FMT_FLIGHTKEY = "yyyyMMdd";
    public static final String APP_FMT_EVENT_PERIOD = "MMMM dd";
    public static final String APP_FMT_ACTIVITY_SCHEDULE = "dd MMM yyyy hh:mm";
    public static final String APP_FMT_FLIGHTKEY_DATETIME = "yyyyMMddHHmmss";
    /**
     * Convert Date to String using constant formats
     *
     * @param date
     * @param format
     * @return
     */
    public static String convertDateToString(Date date, String format) {

        if (date == null) {

            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);

    }

    /**
     * Convert String to Date using constant formats
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Date convertStringToDate(String dateString, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        try {
            return sdf.parse(dateString);

        } catch (Exception exception) {

            return null;
        }
    }

    public static int convertBooleanIntoInt(Boolean boolValue) {

        if (boolValue == null) {

            boolValue = false;

        }

        return boolValue ? 1 : 0;
    }

    public static Boolean converIntToBoolean(int intValue) {

        return (intValue > 0) ? true : false;

    }

    public static Boolean isIntEvenNumber(int number) {

        return (number % 2 == 0) ? true : false;

    }

    public static String optString(JSONObject json, String key)
    {
        if (json.isNull(key))
            return null;
        else
            return json.optString(key, null);
    }

    public static int getHoursOfDate(Date date) {

        if (date == null) {

            return 0;

        }

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);

    }

    public static int getMinutesOfDate(Date date) {

        if (date == null) {

            return 0;

        }

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);

    }

    public static Date setHourAndMinuteOfDate(Date date, int hours, int minutes) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        date = calendar.getTime();

        return date;

    }

    public static Date setDayMonthYearOfDate(int day, int month, int year, Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        date = calendar.getTime();

        return date;


    }


    public static Date addxYearsToDate(int years, final Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);

        return calendar.getTime();

    }


    public static Date addxMonthsToDate(int months, final Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);

        return calendar.getTime();

    }

    public static Date addxDaysToDate(int days, final Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, (days*24));

        Date returnDate = calendar.getTime();

        return returnDate;

    }

    public static Date addxHoursToDate(int hours, final Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);

        return calendar.getTime();

    }

    public static Date addxMinutesToDate(int minutes, final Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();

    }

    public static long getTimeInMillisecondsFromDate(Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();

    }

    public static long getEpochTimeFromDate(Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        return (long)calendar.getTimeInMillis()/1000;

    }

    public static Calendar getCalendarForDate(Date date) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        return calendar;


    }

    public static Date getDateFromMilliseconds(long milliseconds) {

        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar.getTime();


    }

    public static boolean isValidTime(String time) {

        String TIME12HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Pattern pattern = Pattern.compile(TIME12HOURS_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();

    }

    public static boolean stringMatchesPattern(String string, String pattern) {

        Pattern compPatten = Pattern.compile(pattern);
        return compPatten.matcher(string).matches();

    }

    public static String null2Blank(String target) {

        if (target.contentEquals("null") || target == null) {

            return "";
        }

        return target;
    }

    public static JSONArray convertStringArrayToJSONArray(ArrayList<String> array) {

        JSONArray jsonArray = new JSONArray();

        for (int i=0; i < array.size(); i++) {

            jsonArray.put(array.get(i));

        }

        return jsonArray;
    }


    public static double getDoubleFromEditText(EditText edtText){
        double retValue = 0.0;
        try {
            String edtStr = edtText.getText().toString();
            if (edtStr.equals("")){
                retValue = -1.0;
            } else{
                edtStr = edtStr.replace(",", "").replace("$", "");
                retValue = Double.parseDouble(edtStr);
            }
        }catch(Exception e){
            // Crash happens when we capture the editing, and the first letter is - or .
            retValue = -1.0;
        }

        return retValue;
    }

    public static int getIntegerFromEditText(EditText edtText){
        int retValue = 0;
        try {
            retValue = edtText.getText().toString().equals("")? 0: Integer.parseInt(edtText.getText().toString().replace(",", ""));
        }catch(Exception e){
            retValue = 0;
        }

        return retValue;

    }

    public static String getMoneyFormattedStr(double value){
        if (value == 0){
            return "$0.00";
        }else {
            DecimalFormat decimalFormat = new DecimalFormat("#,###,###.00", new DecimalFormatSymbols(Locale.US));

            if (value > 0) {
                return "$" + decimalFormat.format(value);
            }else{
                return "-$" + decimalFormat.format(value * (-1));
            }
        }
    }
}
