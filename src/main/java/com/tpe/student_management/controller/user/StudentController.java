package com.tpe.student_management.controller.user;


import com.tpe.student_management.payload.request.user.StudentRequestDTO;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.service.user.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

    // ************************************************ updateStudentOwnInfo();
    // ---------- Ogrencilerin kendi bilgilerini guncelleyecegi method.
    // ---------- SIFRE guncellenemez!!!

    //************************************************* updateStudentById();
    //---------- Sadece yoneticilerin ogrencileri update edebilecegi method.

    //TODO: ODEV DEGIL - Lesson Program ekleme metodu yazilacak.

    @GetMapping("/change-status/{studentID}/{newStatus}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Map<String, Object>> changeStatusOfStudent(@PathVariable Long studentId,
                                                                     @PathVariable Boolean newStatus){
        return ResponseEntity.ok(studentService.changeStatusOfStudent(studentId, newStatus));
    }
}