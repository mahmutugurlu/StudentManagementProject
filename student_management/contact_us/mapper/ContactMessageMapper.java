package com.tpe.student_management.contact_us.mapper;

import com.tpe.student_management.contact_us.dto.ContactMessageRequestDTO;
import com.tpe.student_management.contact_us.dto.ContactMessageResponseDTO;
import com.tpe.student_management.contact_us.entity.ContactMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContactMessageMapper {
    public ContactMessage mapRequestDTOToContactMessage(ContactMessageRequestDTO dto){
        return ContactMessage.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .subject(dto.getSubject())
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public ContactMessageResponseDTO mapContactMessageToResponseDTO(ContactMessage contactMessage){
        return ContactMessageResponseDTO.builder()
                .id(contactMessage.getId())
                .name(contactMessage.getName())
                .email(contactMessage.getEmail())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .createdAt(contactMessage.getCreatedAt())
                .build();
    }
}
