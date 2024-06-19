package br.gazin.application.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static LocalDate stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(date, formatter);
    }

    public static String dateToString(LocalDate now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }
}
