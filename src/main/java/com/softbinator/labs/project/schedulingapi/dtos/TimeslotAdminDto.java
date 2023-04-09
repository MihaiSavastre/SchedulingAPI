package com.softbinator.labs.project.schedulingapi.dtos;

import com.softbinator.labs.project.schedulingapi.enums.TimeslotStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeslotAdminDto {

    @NotNull
    private String date;

    @NotNull
    private String startHour;

    @NotNull
    private String endHour;

    private TimeslotStatus status;

    private Long doctor;

    private Long patient;

}
