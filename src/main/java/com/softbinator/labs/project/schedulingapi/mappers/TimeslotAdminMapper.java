package com.softbinator.labs.project.schedulingapi.mappers;

import com.softbinator.labs.project.schedulingapi.dtos.TimeslotAdminDto;
import com.softbinator.labs.project.schedulingapi.models.Doctor;
import com.softbinator.labs.project.schedulingapi.models.Patient;
import com.softbinator.labs.project.schedulingapi.models.Timeslot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        uses = {StringTimeMapper.class})
public interface TimeslotAdminMapper {
    TimeslotAdminMapper mapper = Mappers.getMapper(TimeslotAdminMapper.class);

    @Mapping (target = "startHour", source = "startTime", dateFormat = "HH:mm")
    @Mapping(target = "endHour", source = "endTime", dateFormat = "HH:mm")
    TimeslotAdminDto TimeslotToTimeslotAdminDto(Timeslot t);

    default Long map(Doctor doctor) {return doctor.getId();}
    default Long map(Patient patient) {return patient == null ? null : patient.getId();}

    List<TimeslotAdminDto> timeslotsToTimeslotAdminDto(List<Timeslot> timeslots);

}
