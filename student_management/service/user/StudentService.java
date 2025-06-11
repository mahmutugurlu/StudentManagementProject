package com.tpe.student_management.service.user;

import com.tpe.student_management.payload.request.user.StudentRequestDTO;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepository userRepository;

    public ResponseMessage<StudentResponseDTO> saveStudent(StudentRequestDTO dto) {
        //advisor olarak verilen id ile bir kullanici var mi?
        //o kullanici bir advisor teacher mi?
        //unique kontroller
        //sifre encode
        return null;
    }
}
