package com.softbinator.labs.project.schedulingapi.mappers;

import com.softbinator.labs.project.schedulingapi.dtos.DoctorDto;
import com.softbinator.labs.project.schedulingapi.dtos.NewDoctorDto;
import com.softbinator.labs.project.schedulingapi.models.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
@Component
@Mapper (componentModel = "spring",
uses = {StringTimeMapper.class})
public interface DoctorMapper {
    DoctorMapper mapper = Mappers.getMapper(DoctorMapper.class);

    @Mapping(target = "startHour", source = "startHour", dateFormat = "HH:mm")
    @Mapping(target = "endHour", source = "endHour", dateFormat = "HH:mm")
    @Mapping(target = "slotLength", source = "slotLength", qualifiedByName = "stringToDuration")
    public Doctor NewDoctorDtotoDoctor(NewDoctorDto newDoctorDto);

    @Mapping(target = "slotLength", source = "slotLength", qualifiedByName = "durationToString")
    DoctorDto DoctorToDoctorDto (Doctor doctor);
}
