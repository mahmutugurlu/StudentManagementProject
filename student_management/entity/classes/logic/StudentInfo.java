package com.tpe.student_management.entity.classes.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.entity.enums.Note;
import lombok.*;

import javax.persistence.*;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer absence;

    private Double midtermGrade;

    private Double finalGrade;

    private Double averageGrade;

    private String infoNote;

    @Enumerated(EnumType.STRING)
    private Note letterNote;

    @ManyToOne
    @JsonIgnore
    private User teacher;

    @ManyToOne
    @JsonIgnore
    private User student;

    @ManyToOne
    private Lesson lesson;

    @OneToOne
    private EducationTerm educationTerm;


}
