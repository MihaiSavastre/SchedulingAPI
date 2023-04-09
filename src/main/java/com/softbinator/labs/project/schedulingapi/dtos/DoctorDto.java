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
public class DoctorDto {

        @NotNull
        private Long id;

        @NotNull
        private String name;

        @NotNull
        private String startHour;

        @NotNull
        private String endHour;

        @NotNull
        private String slotLength;

}
