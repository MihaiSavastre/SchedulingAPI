package com.softbinator.labs.project.schedulingapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private List<TimeslotAdminDto> appointments;

}
