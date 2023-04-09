package com.softbinator.labs.project.schedulingapi.security.dtos;


import com.softbinator.labs.project.schedulingapi.dtos.NewDoctorDto;
import com.softbinator.labs.project.schedulingapi.security.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDtoDoctor extends CreateUserDto{
    //private Role role = Role.DOCTOR;

    @NotNull
    private NewDoctorDto newDoctorDto;
}
