package com.softbinator.labs.project.schedulingapi.services;

import com.softbinator.labs.project.schedulingapi.dtos.NewPatientDto;
import com.softbinator.labs.project.schedulingapi.dtos.PatientDto;
import com.softbinator.labs.project.schedulingapi.dtos.TimeslotAdminDto;
import com.softbinator.labs.project.schedulingapi.mappers.PatientMapper;
import com.softbinator.labs.project.schedulingapi.mappers.TimeslotAdminMapper;
import com.softbinator.labs.project.schedulingapi.models.Patient;
import com.softbinator.labs.project.schedulingapi.repositories.PatientRepository;
import com.softbinator.labs.project.schedulingapi.repositories.TimeslotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final TimeslotRepository timeslotRepository;
    private final PatientMapper patientMapper;
    private final TimeslotAdminMapper timeslotAdminMapper;

    public PatientDto create(NewPatientDto newPatientDto) {
        Patient patient = patientRepository.save(
                Patient.builder()
                .name(newPatientDto.getName())
                .build());
        return patientMapper.patientToPatientDto(patient);
    }

    public List<TimeslotAdminDto> seeAppointments(Long patientId) {
        //returns a list of appointments for the current logged patient
        return timeslotAdminMapper.timeslotsToTimeslotAdminDto(
                timeslotRepository.findAllByPatient_Id(patientId));
    }
}
