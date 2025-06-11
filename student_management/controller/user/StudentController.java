package com.tpe.student_management.controller.user;

import com.tpe.student_management.payload.request.user.StudentRequestDTO;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<StudentResponseDTO>> saveStudent(@RequestBody @Valid StudentRequestDTO dto){
        return new ResponseEntity<>(studentService.saveStudent(dto), HttpStatus.CREATED);
    }
}
