package com.softbinator.labs.project.schedulingapi.repositories;

import com.softbinator.labs.project.schedulingapi.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
