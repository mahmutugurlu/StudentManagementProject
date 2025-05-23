package com.tpe.student_management.controller.user;

import com.tpe.student_management.payload.request.user.TeacherRequestDTO;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.payload.response.user.TeacherResponseDTO;
import com.tpe.student_management.service.user.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<TeacherResponseDTO>> saveTeacher(@RequestBody @Valid TeacherRequestDTO dto){
        return new ResponseEntity<>(teacherService.saveTeacher(dto), HttpStatus.CREATED);
    }

    @GetMapping("/advisor/get-all-students")
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudentsOfAdvisorByUsername(HttpServletRequest request){
        return ResponseEntity.ok(
                teacherService.getAllStudentsOfAdvisorByUsername((String) request.getAttribute("username")));
    }















    //********************  updateTeacherById()
    //********************  makeTeacherAdvisorById()
    //--- teacher ----> advisor teacher
    //--- Teacher icin isAdvisor'i true yapacaksiniz.
    //--- DIKKAT - Gelen user teacher mi? halihazirda advisor mi?

    //********************  makeAdvisorTeacherTeacherById()
    //--- advisor teacher ----> teacher
    //--- Teacher icin isAdvisor'i false yapacaksiniz.
    //--- DIKKAT - Gelen user teacher mi?

    //********************  getAllAdvisorTeacher()

}
