package com.excilys.computer_database.core;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateHelper {
    public static LocalDate timestampToLocalDate(Timestamp t) {
        return t.toLocalDateTime().toLocalDate();
    }

    public static Timestamp localDateToTimestamp(LocalDate d){
        return Timestamp.valueOf(d.atStartOfDay());
    }

    public static LocalDate isoStringToLocalDate(String iso) throws DateTimeParseException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(iso, format);
    }
}