package com.softbinator.labs.project.schedulingapi;

import com.softbinator.labs.project.schedulingapi.dtos.DoctorDto;
import com.softbinator.labs.project.schedulingapi.dtos.NewDoctorDto;
import com.softbinator.labs.project.schedulingapi.mappers.DoctorMapper;
import com.softbinator.labs.project.schedulingapi.models.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalTime;

@SpringBootTest
class SchedulingApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testDoctorMapper() {
        NewDoctorDto dto = new NewDoctorDto("nume1" , "08:00", "15:30", "00:30");

        Doctor doctor = DoctorMapper.mapper.NewDoctorDtotoDoctor(dto);

//        System.out.println(doctor.getName());
//        System.out.println(doctor.getStartHour());
//        System.out.println(doctor.getEndHour());
//        System.out.println(doctor.getSlotLength());

        Doctor doc = Doctor.builder()
                .id(4L)
                .name("nume2")
                .startHour(LocalTime.of(10, 30))
                .endHour(LocalTime.of(17, 0))
                .slotLength(Duration.ofMinutes(90))
                .build();

//        DoctorDto getDTO = DoctorMapper.mapper.DoctorToDoctorDto(doc);
//        System.out.println(getDTO.getId());
//        System.out.println(getDTO.getName());
//        System.out.println(getDTO.getStartHour());
//        System.out.println(getDTO.getEndHour());
//        System.out.println(getDTO.getSlotLength());
    }

}
