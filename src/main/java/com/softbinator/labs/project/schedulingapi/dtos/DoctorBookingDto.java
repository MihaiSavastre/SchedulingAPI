package com.softbinator.labs.project.schedulingapi.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoctorBookingDto {

    @NotNull
    private String date;

    private String startTime;

    private String endTime;

    private Long patientId = null;

}
