package com.tpe.student_management.controller.logic;

import com.tpe.student_management.service.logic.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/education-term")
@RequiredArgsConstructor
public class EducationTermController {
    private EducationTermService educationTermService;


} //g