package com.softbinator.labs.project.schedulingapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
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
