package com.softbinator.labs.project.schedulingapi.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;

@Component
@Mapper
public interface StringTimeMapper {
    @Named("stringToDuration")
    static Duration stringToDuration(String s) {
        //verify HH:mm format
        checkTimeFormat(s);
        int hour = Integer.parseInt(s.substring(0,2));
        int minute = Integer.parseInt(s.substring(3, 5));
        if (hour > 24 || minute > 60) {
            throw new RuntimeException("Illegal time value!");
        }
        return Duration.ofMinutes(hour * 60L + minute);
    }
    @Named("durationToString")
    static String durationToString(Duration d) {
        //return string in format HH:mm
        return String.format("%02d:%02d", d.toMinutes() / 60, d.toMinutes() % 60);
    }

    static void checkTimeFormat(String s) {
        if (!s.matches("\\d\\d:\\d\\d")) {
            throw new RuntimeException("Invalid time format!");
        }
    }

   static LocalTime stringToTime(String s) {
        //verify HH:mm format
        checkTimeFormat(s);
        int hour = Integer.parseInt(s.substring(0,2));
        int minute = Integer.parseInt(s.substring(3, 5));
        if (hour > 24 || minute > 60) {
            throw new RuntimeException("Illegal time value!");
        }
        return LocalTime.of(hour, minute);
    }
}
