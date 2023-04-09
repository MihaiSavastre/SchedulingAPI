package com.softbinator.labs.project.schedulingapi.controllers;

import com.softbinator.labs.project.schedulingapi.security.services.UserInfoService;
import com.softbinator.labs.project.schedulingapi.services.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Patient control",description = "Used by patients to check their appointments")
@RequestMapping("/api/v1/patients")
@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final UserInfoService userInfoService;

    //Implementation meant for use with security. Takes doctor's id from the info of the connected user
    //not as parameter

//    @GetMapping
//    @PreAuthorize("hasAuthority('PATIENT')")
//    public ResponseEntity<?> getBookings() {
//        return new ResponseEntity<>(patientService.seeAppointments(userInfoService.getEntityId()), HttpStatus.OK);
//    }

    //Standard impl
    @GetMapping
    public ResponseEntity<?> getBookings(@RequestParam Long id) {
        return new ResponseEntity<>(patientService.seeAppointments(id), HttpStatus.OK);
    }
}
