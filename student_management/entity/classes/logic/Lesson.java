package com.tpe.student_management.entity.classes.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer creditScore;

    private Boolean isMandatory;

    @ManyToMany(mappedBy = "lessons", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<LessonProgram> lessonPrograms;
}
