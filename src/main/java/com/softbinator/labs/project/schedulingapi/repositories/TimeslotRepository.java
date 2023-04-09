package com.softbinator.labs.project.schedulingapi.repositories;

import com.softbinator.labs.project.schedulingapi.models.Doctor;
import com.softbinator.labs.project.schedulingapi.models.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {

    List<Timeslot> findAllByDateAndDoctor(LocalDate date, Doctor doctor);

    List<Timeslot> findAllByDate(LocalDate date);
    List<Timeslot> findAllByDateBetween(LocalDate start, LocalDate end);
    List<Timeslot> findAllByDoctor_Id(Long doctorId);
    List<Timeslot> findAllByPatient_Id(Long pattientId);

    Optional<Timeslot> findByDateAndDoctorAndStartTime(LocalDate date, Doctor doctor, LocalTime startTime);

}
