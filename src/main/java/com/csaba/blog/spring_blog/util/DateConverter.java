package com.csaba.blog.spring_blog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private DateConverter() {}

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date convertStringToDate(String dateString) {

        try {
            return sdf.parse(dateString);
        } catch (ParseException | NullPointerException e ) {
            return null;
        }
    }

    public static String convertDateToString(Date date) {
        return sdf.format(date);
    }
}
