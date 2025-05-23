package com.tpe.student_management.entity.classes.logic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.entity.enums.Day;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Day day;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
    private LocalTime endTime;

    @ManyToMany
    @JoinTable(
            name = "lesson_program_lesson",
            joinColumns = @JoinColumn(name = "lesson_program_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<Lesson> lessons;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private EducationTerm educationTerm;

    @ManyToMany(mappedBy = "lessonProgramList", fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<User> users;

    @PreRemove
    private void removeLessonProgramFromOwningUser(){
        users.forEach(user -> user.getLessonProgramList().remove(this));
    }
}
