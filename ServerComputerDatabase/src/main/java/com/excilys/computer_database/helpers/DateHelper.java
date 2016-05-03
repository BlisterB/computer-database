package com.excilys.computer_database.helpers;

import java.sql.Timestamp;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateHelper {
    public static LocalDate timestampToLocalDate(Timestamp t) {
        return new LocalDate(t);
    }

    public static Timestamp localDateToTimestamp(LocalDate d){
        return new Timestamp(d.toDateTimeAtStartOfDay().getMillis());
    }

    public static LocalDate isoStringToLocalDate(String iso) {
        DateTimeFormatter parser = ISODateTimeFormat.dateParser();
        return parser.parseLocalDate(iso);
    }
}