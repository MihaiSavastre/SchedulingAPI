package com.softbinator.labs.project.schedulingapi.controllers;

import com.softbinator.labs.project.schedulingapi.dtos.BookingInfoDto;
import com.softbinator.labs.project.schedulingapi.dtos.DoctorBookingDto;
import com.softbinator.labs.project.schedulingapi.dtos.PatientBookingDto;
import com.softbinator.labs.project.schedulingapi.security.services.UserInfoService;
import com.softbinator.labs.project.schedulingapi.services.TimeslotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Timeslot Controller", description = "Commands for creating appointments")
@RequestMapping("/api/v1/timeslots")
@RestController
@RequiredArgsConstructor
public class TimeslotController {

    private final TimeslotService timeslotService;
    private final UserInfoService userInfoService;

    @GetMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getBookings(BookingInfoDto bookingInfoDto) {
        return new ResponseEntity<>(timeslotService.getAll(bookingInfoDto), HttpStatus.OK);
    }

    //Implementation meant for security functionality, getting id from connected user
//    @PostMapping("/doctor")
//    @PreAuthorize("hasAuthority('DOCTOR')")
//    public ResponseEntity<?> doctorBooking(@RequestBody DoctorBookingDto doctorBookingDto) {
//        timeslotService.createDoctorBooking(doctorBookingDto, userInfoService.getEntityId());
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @PostMapping("/patient")
//    @PreAuthorize("hasAuthority('PATIENT')")
//    public ResponseEntity<?> patientBooking(@RequestBody PatientBookingDto patientBookingDto) {
//        timeslotService.createPatientBooking(patientBookingDto, userInfoService.getEntityId());
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @Tag(name = "Doctor Reservation",description = "Used by doctors to reserve timeslots")
    @PostMapping("/doctor")
    public ResponseEntity<?> doctorBooking(@RequestBody DoctorBookingDto doctorBookingDto, @RequestParam Long doctorId) {
        timeslotService.createDoctorBooking(doctorBookingDto, doctorId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Tag(name = "Patient Booking",description = "Used by patients to book timeslots")
    @PostMapping("/patient")
    public ResponseEntity<?> patientBooking(@RequestBody PatientBookingDto patientBookingDto, @RequestParam Long patientId) {
        timeslotService.createPatientBooking(patientBookingDto, patientId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
