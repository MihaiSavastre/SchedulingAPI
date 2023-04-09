package com.softbinator.labs.project.schedulingapi.repositories;


import com.softbinator.labs.project.schedulingapi.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findDoctorById(Long id);

}
