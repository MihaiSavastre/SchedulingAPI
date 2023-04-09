package com.softbinator.labs.project.schedulingapi.security.controllers;

import com.softbinator.labs.project.schedulingapi.dtos.DoctorDto;
import com.softbinator.labs.project.schedulingapi.dtos.PatientDto;
import com.softbinator.labs.project.schedulingapi.security.dtos.AuthentificationRequest;
import com.softbinator.labs.project.schedulingapi.security.dtos.CreateUserDtoDoctor;
import com.softbinator.labs.project.schedulingapi.security.dtos.CreateUserDtoPatient;
import com.softbinator.labs.project.schedulingapi.security.enums.Role;
import com.softbinator.labs.project.schedulingapi.security.services.UserService;
import com.softbinator.labs.project.schedulingapi.services.DoctorService;
import com.softbinator.labs.project.schedulingapi.services.PatientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Controller",description = "Manages registration and logging in")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @PostMapping("/register/doctor")
    public ResponseEntity<Object> register(@Valid @RequestBody CreateUserDtoDoctor createUserDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        DoctorDto docDto = doctorService.create(createUserDto.getNewDoctorDto());
        return ResponseEntity.ok(userService.createUser(createUserDto, Role.DOCTOR ,docDto.getId()));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<Object> register(@Valid @RequestBody CreateUserDtoPatient createUserDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        PatientDto patientDto = patientService.create(createUserDto.getNewPatientDto());
        return ResponseEntity.ok(userService.createUser(createUserDto, Role.PATIENT ,patientDto.getId()));
    }

    @PostMapping
    public ResponseEntity<Object> authenticate(@RequestBody AuthentificationRequest authRequest) {
        return ResponseEntity.ok(userService.authenticate(authRequest));
    }

}

