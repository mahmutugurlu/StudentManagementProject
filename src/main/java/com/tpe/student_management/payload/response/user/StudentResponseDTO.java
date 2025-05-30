package com.tpe.student_management.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tpe.student_management.entity.classes.logic.LessonProgram;
import com.tpe.student_management.payload.response.abstracts.BaseUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponseDTO extends BaseUserResponse {
    private int studentNumber;
    private String motherName;
    private String fatherName;
    private boolean isActive;

    private Set<LessonProgram> lessonProgramList;
}
