package com.excilys.computer_database.helpers;

import java.sql.Timestamp;

import org.joda.time.LocalDate;

public class DateHelper {
    public static LocalDate timestampToLocalDate(Timestamp t) {
        return new LocalDate(t);
    }

    public static Timestamp localDateToTimestamp(LocalDate d){
        return new Timestamp(d.toDateTimeAtStartOfDay().getMillis());
    }
}