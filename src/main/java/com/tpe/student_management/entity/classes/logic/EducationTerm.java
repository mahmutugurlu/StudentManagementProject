package com.tpe.student_management.entity.classes.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.student_management.entity.enums.Term;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EducationTerm {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Education term must be provided.")
    @Enumerated(EnumType.STRING)
    private Term term;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    @NotNull(message = "Term start date must be provided.")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    @NotNull(message = "Term end date must be provided.")
    private LocalDate endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "last_registration_date")
    @NotNull(message = "Term last registration date must be provided.")
    private LocalDate lastRegistrationDate;
}