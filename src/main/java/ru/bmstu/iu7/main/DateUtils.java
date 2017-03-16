package ru.bmstu.iu7.main;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parseDate(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        try {
            return DATE_FORMAT.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format(Date date) {
        return date == null ? "" : DATE_FORMAT.format(date);
    }
}
