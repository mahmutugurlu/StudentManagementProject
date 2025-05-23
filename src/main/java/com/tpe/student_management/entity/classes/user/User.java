package com.tpe.student_management.entity.classes.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpe.student_management.entity.classes.logic.LessonProgram;
import com.tpe.student_management.entity.classes.logic.Meet;
import com.tpe.student_management.entity.classes.logic.StudentInfo;
import com.tpe.student_management.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_user")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String ssn;

    private String firstName;

    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String birthPlace;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private Boolean builtIn;

    private String motherName;

    private String fatherName;

    private int studentNumber;

    private boolean isActive;

    private Long advisorTeacherId;

    private Boolean isAdvisor;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne // Her zaman oneToOne iliskiyi belirtmez.
    private UserRole userRole;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private List<StudentInfo> studentInfos;

    @ManyToMany
    @JoinTable(
            name = "user_lesson_program",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_program_id")
    )
    private Set<LessonProgram> lessonProgramList;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "meet_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "meet_id")
    )
    private List<Meet> meetList;
}
