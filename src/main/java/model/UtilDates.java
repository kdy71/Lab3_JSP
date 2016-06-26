package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dmitry Khoruzhenko on 08.01.2016.
 * Class that contains util methods for working with dates.
 */
public class UtilDates {

    public static final SimpleDateFormat SDF_RUS_MS = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss .SSS");
    public static final SimpleDateFormat SDF_RUS_DAY = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat SDF_USA_DAY = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat DATA_BASE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Convert date to string in format "dd.MM.yyyy HH:mm:ss"  or String "null" if date is null
     *
     * @param date - Date for formatting
     * @return date as String in format "dd.MM.yyyy HH:mm:ss"  or String "null" if date is null
     */
    public static String dateToString(Date date) {
        if (date == null)
            return "null";
        else
            return SDF_USA_DAY.format(date);
    }

    public static String dateToDBString(Date date) {
        if (date == null)
            return "null";
        else
            return DATA_BASE_FORMAT.format(date);
    }

    public static String nowToString() {
        return SDF_RUS_MS.format(new Date());
    }

    /**
     * Convert string (dd.MM.yyyy) to date.
     *
     * @param dateAsString string representation of date
     * @return Date from String
     * @throws ParseException
     */
    public static Date stringToDate(String dateAsString) throws ParseException {
        return SDF_USA_DAY.parse(dateAsString);
    }
}
