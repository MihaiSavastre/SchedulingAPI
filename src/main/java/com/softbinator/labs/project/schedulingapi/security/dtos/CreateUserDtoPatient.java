package com.softbinator.labs.project.schedulingapi.security.dtos;

import com.softbinator.labs.project.schedulingapi.dtos.NewPatientDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDtoPatient extends CreateUserDto{

    //private Role role = Role.PATIENT;

    @NotNull
    private NewPatientDto newPatientDto;

}
