package com.softbinator.labs.project.schedulingapi.controllers;

import com.softbinator.labs.project.schedulingapi.security.services.UserInfoService;
import com.softbinator.labs.project.schedulingapi.services.DoctorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Doctor control",description = "Patients can see a doctor's free spots, doctors can check their own program")
@RequestMapping("/api/v1/doctors")
@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final UserInfoService userInfoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAvailableSlots(@PathVariable Long id, @RequestParam String date) {
        return new ResponseEntity<>(doctorService.getCalendar(id, date), HttpStatus.OK);
    }

    //Implementation meant for use with security. Takes doctor's id from the info of the connected user
    //not as parameter

//    @GetMapping
//    @PreAuthorize("hasAuthority('DOCTOR')")
//    public ResponseEntity<?> getBookings() {
//        return new ResponseEntity<>(doctorService.getBookings(userInfoService.getEntityId()), HttpStatus.OK);
//    }

    //Standard implementation
    @GetMapping("/personal_program")
    public ResponseEntity<?> getBookings(@RequestParam Long id) {
        return new ResponseEntity<>(doctorService.getBookings(id), HttpStatus.OK);
    }
}
