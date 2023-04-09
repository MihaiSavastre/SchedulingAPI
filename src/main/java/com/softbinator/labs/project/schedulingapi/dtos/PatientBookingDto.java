package com.softbinator.labs.project.schedulingapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientBookingDto {

    @NotNull
    private String date;

    @NotNull
    private String startTime;

    @NotNull
    private Long doctorId;
}
