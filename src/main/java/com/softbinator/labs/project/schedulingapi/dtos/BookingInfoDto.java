package com.softbinator.labs.project.schedulingapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingInfoDto {

    private String date;

    private String month;

    private Long doctorId;

    private Long patientId;
}
