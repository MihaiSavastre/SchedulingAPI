package com.softbinator.labs.project.schedulingapi.mappers;

import com.softbinator.labs.project.schedulingapi.dtos.PatientDto;
import com.softbinator.labs.project.schedulingapi.models.Patient;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        uses = {TimeslotAdminMapper.class})
public interface PatientMapper {

    Patient patientDtoToPatient(PatientDto patientDto);

    PatientDto patientToPatientDto(Patient patient);

}
