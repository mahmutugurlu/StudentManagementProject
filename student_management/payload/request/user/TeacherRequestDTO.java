package com.tpe.student_management.payload.request.user;

import com.tpe.student_management.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TeacherRequestDTO extends BaseUserRequest {

    @NotNull(message = "Please select lesson.")
    private Set<Long> lessonIdList;

    @NotNull(message = "Please select if the teacher is and advisor teacher or not.")
    private Boolean isAdvisor;
}
