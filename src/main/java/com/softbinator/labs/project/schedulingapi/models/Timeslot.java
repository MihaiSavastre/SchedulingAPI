package com.softbinator.labs.project.schedulingapi.models;

import com.softbinator.labs.project.schedulingapi.enums.TimeslotStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "timeslots")
@SQLDelete(sql = "UPDATE timeslots SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Timeslot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    @Column(name = "start_time")
    private LocalTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalTime endTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TimeslotStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient = null;

    private boolean deleted = Boolean.FALSE;
}
