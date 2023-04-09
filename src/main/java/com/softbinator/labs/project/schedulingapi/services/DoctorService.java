package com.softbinator.labs.project.schedulingapi.services;

import com.softbinator.labs.project.schedulingapi.dtos.DoctorDto;
import com.softbinator.labs.project.schedulingapi.dtos.NewDoctorDto;
import com.softbinator.labs.project.schedulingapi.dtos.TimeslotAdminDto;
import com.softbinator.labs.project.schedulingapi.dtos.TimeslotUserDto;
import com.softbinator.labs.project.schedulingapi.mappers.DoctorMapper;
import com.softbinator.labs.project.schedulingapi.mappers.StringDateMapper;
import com.softbinator.labs.project.schedulingapi.mappers.StringTimeMapper;
import com.softbinator.labs.project.schedulingapi.mappers.TimeslotAdminMapper;
import com.softbinator.labs.project.schedulingapi.models.Doctor;
import com.softbinator.labs.project.schedulingapi.models.Timeslot;
import com.softbinator.labs.project.schedulingapi.repositories.DoctorRepository;
import com.softbinator.labs.project.schedulingapi.repositories.TimeslotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorMapper doctorMapper;
    private final StringDateMapper stringDateMapper;
    private final TimeslotAdminMapper timeslotAdminMapper;
    private final DoctorRepository doctorRepository;
    private final TimeslotRepository timeslotRepository;

    public DoctorDto create(NewDoctorDto newDoctorDto) {
        validateDto(newDoctorDto);
        Doctor doc = doctorMapper.NewDoctorDtotoDoctor(newDoctorDto);
        doc = doctorRepository.save(doc);
        return doctorMapper.DoctorToDoctorDto(doc);
    }

    void validateDto(NewDoctorDto newDoctorDto) {
        //1. Good string format and time values
        LocalTime start = StringTimeMapper.stringToTime(newDoctorDto.getStartHour());
        LocalTime end = StringTimeMapper.stringToTime(newDoctorDto.getEndHour());
        Duration slotLength = StringTimeMapper.stringToDuration(newDoctorDto.getSlotLength());

        //2. Check if slot length can cover the working schedule
        if (MINUTES.between(start, end) % slotLength.toMinutes() != 0) {
            throw new RuntimeException("Slot does not fit program!");
        }
    }

    public List<TimeslotUserDto> getCalendar(Long id, String date) {
        Doctor doc = doctorRepository.findDoctorById(id)
                .orElseThrow(
                        () -> {throw new RuntimeException("Doctor not found!");}
                );


        if (date.matches("\\d\\d-\\d\\d-\\d\\d\\d\\d")) {
            LocalDate day = stringDateMapper.getDate(date);
            return getDay(doc, day);
        }

        //generate a whole month

        //if date is not "MM-yyyy", StringDateMapper will throw an error
        LocalDate firstDay = stringDateMapper.getDate("01-" + date);
        LocalDate lastDay = firstDay.plusDays(firstDay.lengthOfMonth() - 1);

        List<TimeslotUserDto> free = new ArrayList<>();

        for (LocalDate day = firstDay; day.isBefore(lastDay); day = day.plusDays(1)) {
            free.addAll(getDay(doc, day));
        }

        return free;
    }

    List<TimeslotUserDto> getDay(Doctor doc, LocalDate date) {
        if (!stringDateMapper.isWorkingDay(date))
            return new ArrayList<>();

        List<Timeslot> occupied = timeslotRepository.findAllByDateAndDoctor(date, doc);
        List<TimeslotUserDto> free = new ArrayList<>();

        boolean notBooked;
        for (LocalTime t = doc.getStartHour(); t.isBefore(doc.getEndHour()); t = t.plus(doc.getSlotLength())) {
            notBooked = true;
            for (var slot : occupied) {
                if (slot.getStartTime().equals(t)) {
                    notBooked = false;
                    break;
                }
            }
            if (notBooked) {
                free.add(new TimeslotUserDto(
                        date.toString(),
                        t.toString(),
                        t.plus(doc.getSlotLength()).toString()
                ));
            }
        }
        return free;
    }

    public List<TimeslotAdminDto> getBookings(Long doctorId) {
        return timeslotAdminMapper.timeslotsToTimeslotAdminDto(timeslotRepository.findAllByDoctor_Id(doctorId));
    }
}


