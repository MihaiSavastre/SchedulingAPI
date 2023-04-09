package com.softbinator.labs.project.schedulingapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Duration;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "doctors")
@SQLDelete(sql = "UPDATE doctors SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "start_hour")
    private LocalTime startHour;

    @NotNull
    @Column(name = "end_hour")
    private LocalTime endHour;

    @NotNull
    @Column(name = "slot_length")
    private Duration slotLength;

    private boolean deleted = Boolean.FALSE;
}
