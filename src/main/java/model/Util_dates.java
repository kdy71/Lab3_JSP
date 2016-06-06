package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilites and constants for Lab 1 (Task Manager)
 * Created by Dmitry Khoruzhenko on 08.01.2016.
 */
public class Util_dates {

    public static final SimpleDateFormat sdf_rus_ms  = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss .SSS");
    public static final SimpleDateFormat sdf_rus_day = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat sdf_usa_day = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat dataBaseFormat = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * Convert date to string in format "dd.MM.yyyy HH:mm:ss"  or String "null" if date is null
     *
     * @param dat1 - Date for formatting
     * @return date as String in format "dd.MM.yyyy HH:mm:ss"  or String "null" if date is null
     */
    public static String dat2Str(Date dat1) {
        if (dat1 == null)
            return "null";
        else
            return sdf_usa_day.format(dat1);
    }

    public static String dateToDBString(Date date) {
        if (date == null)
            return "null";
        else
            return dataBaseFormat.format(date);
    }
    /**
     * Возвращает текущее время в строковом виде
     * @return
     */
    public static String now2Str() {
        return sdf_rus_ms.format(new Date());
    }


    /**
     * Convert string (dd.MM.yyyy) to date
     *
     * @param stDate
     * @return Date from String
     * @throws ParseException
     */
    public static Date str2Date(String stDate) throws ParseException {
        return sdf_usa_day.parse(stDate);
    }

}
