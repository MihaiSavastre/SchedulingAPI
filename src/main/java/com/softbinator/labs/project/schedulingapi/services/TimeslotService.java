package com.softbinator.labs.project.schedulingapi.services;

import com.softbinator.labs.project.schedulingapi.dtos.BookingInfoDto;
import com.softbinator.labs.project.schedulingapi.dtos.DoctorBookingDto;
import com.softbinator.labs.project.schedulingapi.dtos.PatientBookingDto;
import com.softbinator.labs.project.schedulingapi.dtos.TimeslotAdminDto;
import com.softbinator.labs.project.schedulingapi.enums.TimeslotStatus;
import com.softbinator.labs.project.schedulingapi.mappers.StringDateMapper;
import com.softbinator.labs.project.schedulingapi.mappers.StringTimeMapper;
import com.softbinator.labs.project.schedulingapi.mappers.TimeslotAdminMapper;
import com.softbinator.labs.project.schedulingapi.models.Doctor;
import com.softbinator.labs.project.schedulingapi.models.Patient;
import com.softbinator.labs.project.schedulingapi.models.Timeslot;
import com.softbinator.labs.project.schedulingapi.repositories.DoctorRepository;
import com.softbinator.labs.project.schedulingapi.repositories.PatientRepository;
import com.softbinator.labs.project.schedulingapi.repositories.TimeslotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final StringDateMapper stringDateMapper;
    //private final StringTimeMapper stringTimeMapper;


    /**
     * Initially wanted to have all fields as optional parameters, so admin
     * could see all the timeslots and filter them by doctor, patient, date,
     * month, in whatever combination.
     * However, implementation proves to difficult (JPA Specification seems key)
     * For now, admin can only choose one criteria of filtering
     * @param bookingInfoDto
     * @return
     */
    public ResponseEntity<?> getAll(BookingInfoDto bookingInfoDto) {
        List<TimeslotAdminDto> timeslots = new ArrayList<>();
        if (bookingInfoDto.getDate() != null) {
            timeslots =  TimeslotAdminMapper.mapper
                    .timeslotsToTimeslotAdminDto(
                            timeslotRepository.findAllByDate(stringDateMapper.getDate(bookingInfoDto.getDate())));
        }
        if (bookingInfoDto.getMonth() != null) {
            LocalDate firstDay = stringDateMapper.getDateFromMonth(bookingInfoDto.getMonth());
            timeslots = TimeslotAdminMapper.mapper
                    .timeslotsToTimeslotAdminDto(timeslotRepository.findAllByDateBetween(
                        firstDay , firstDay.plus(firstDay.lengthOfMonth(), ChronoUnit.DAYS) ));
        }
        if (bookingInfoDto.getDoctorId() != null) {
            timeslots = TimeslotAdminMapper.mapper.timeslotsToTimeslotAdminDto(timeslotRepository.findAllByDoctor_Id(
                    bookingInfoDto.getDoctorId()));
        }
        if (bookingInfoDto.getPatientId() != null) {
            timeslots = TimeslotAdminMapper.mapper.timeslotsToTimeslotAdminDto(timeslotRepository.findAllByPatient_Id(
                    bookingInfoDto.getPatientId()));
        }

        return new ResponseEntity<>(timeslots, HttpStatus.OK);
    }

    public void createDoctorBooking(DoctorBookingDto doctorBookingDto, Long doctorId) {
        LocalDate day = stringDateMapper.getDate(doctorBookingDto.getDate());
        Doctor doc = doctorRepository.findById(doctorId).orElseThrow(
                () -> {throw new RuntimeException("Bad Request!");}
        );

        LocalTime start;
        LocalTime end;

        if (doctorBookingDto.getStartTime() == null && doctorBookingDto.getEndTime() == null) {
            //whole day reserved
            start = doc.getStartHour();
            end = doc.getEndHour();
        }
        else if (doctorBookingDto.getStartTime() == null || doctorBookingDto.getEndTime() == null) {
            throw new RuntimeException("Invalid request!");
        }
        else {
            start = StringTimeMapper.stringToTime(doctorBookingDto.getStartTime());
            end = StringTimeMapper.stringToTime(doctorBookingDto.getEndTime());
            //now to make sure this interval covers exactly a number of normal timeslots
            //we decrease the start until it coincides with the start of a timeslot, and
            //increase the end with the same goal

            //|-----|-----|-----|-----|-----|-----|-----|
            //       <a>|---|<b>
            //First line represents the usual timeslots, in order
            //Second line is a supposed doctor reservation, not in line with usual timeslots
            //Duration a is subtracted from the start of the reservation
            //and duration b is added. In the end, the reservation will be 2 timeslots
            //long, both of which will be added to the timeslots table
            start = start.minus(
                    Duration.between(doc.getStartHour(), start).toMinutes() %
                            doc.getSlotLength().toMinutes(), ChronoUnit.MINUTES);
            end = end.plus(doc.getSlotLength().toMinutes() -
                    Duration.between(doc.getStartHour(), end).toMinutes() %
                    doc.getSlotLength().toMinutes(), ChronoUnit.MINUTES);
        }
        TimeslotStatus status;
        Patient patient = null;
        if (doctorBookingDto.getPatientId() == null) {
            status = TimeslotStatus.RESERVED;
        }
        else {
            status = TimeslotStatus.BOOKED;
            patient = patientRepository.findById(doctorBookingDto.getPatientId()).orElseThrow(
                    () -> {throw new RuntimeException("Invalid patient id");}
            );
        }

        for (LocalTime slot = start; slot.isBefore(end); slot = slot.plus(doc.getSlotLength())) {
            Timeslot newSlot = Timeslot.builder()
                    .date(day)
                    .startTime(slot)
                    .endTime(slot.plus(doc.getSlotLength()))
                    .status(status)
                    .doctor(doc)
                    .patient(patient)
                    .build();
            timeslotRepository.save(newSlot);
        }

    }

    public void createPatientBooking(PatientBookingDto patientBookingDto, Long patientId) {
        LocalDate day = stringDateMapper.getDate(patientBookingDto.getDate());
        Doctor doc = doctorRepository.findById(patientBookingDto.getDoctorId()).orElseThrow(
                () -> {throw new RuntimeException("Bad Request!");}
        );
        Patient patient = patientRepository.findById(patientId).get();
        LocalTime start = StringTimeMapper.stringToTime(patientBookingDto.getStartTime());

        if (Duration.between(doc.getStartHour(), start).toMinutes() %
                doc.getSlotLength().toMinutes() != 0 ) {
            throw new RuntimeException("Invalid timeslot!");
        }

        timeslotRepository.findByDateAndDoctorAndStartTime(day, doc, start).ifPresent(
                (t) -> {throw new RuntimeException("Timeslot unavailable!");}
        );

        timeslotRepository.save(Timeslot.builder()
                .date(day)
                .startTime(start)
                .endTime(start.plus(doc.getSlotLength()))
                .status(TimeslotStatus.BOOKED)
                .doctor(doc)
                .patient(patient)
                .build() );

    }
}
