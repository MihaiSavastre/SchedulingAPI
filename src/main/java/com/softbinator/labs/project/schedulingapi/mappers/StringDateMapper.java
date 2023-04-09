package com.softbinator.labs.project.schedulingapi.mappers;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringDateMapper {
    public LocalDate getDate(String s) {
        if (s == null) return null;
        if (!s.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d"))
            throw new RuntimeException("Invalid Date Format!");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate day = LocalDate.parse(s, formatter);
        if (!isWorkingDay(day))
            throw new RuntimeException("Not working day!");
        return LocalDate.parse(s, formatter);
    }

    public LocalDate getDateFromMonth(String s) {
        if (s == null) return null;
        if (!s.matches("\\d\\d-\\d\\d\\d\\d"))
            throw new RuntimeException("Invalid Date Format!");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse("01" + s, formatter);
    }

    public boolean isWorkingDay(LocalDate day) {
        if (day.getDayOfWeek().name().equals("SATURDAY") || day.getDayOfWeek().name().equals("SUNDAY"))
            return false;
        return true;
    }
}
