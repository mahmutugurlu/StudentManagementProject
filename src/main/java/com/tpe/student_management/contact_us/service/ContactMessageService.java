package com.tpe.student_management.contact_us.service;


import com.tpe.student_management.contact_us.dto.ContactMessageRequestDTO;
import com.tpe.student_management.contact_us.dto.ContactMessageResponseDTO;
import com.tpe.student_management.contact_us.entity.ContactMessage;
import com.tpe.student_management.contact_us.mapper.ContactMessageMapper;
import com.tpe.student_management.contact_us.repository.ContactMessageRepository;
import com.tpe.student_management.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper mapper;

    public ResponseMessage<ContactMessageResponseDTO> saveContactMessage(ContactMessageRequestDTO dto) {
        //modelMapperda da 1 satirda halletmistik.
        ContactMessage contactMessage = mapper.mapRequestDTOToContactMessage(dto);

        ContactMessage savedContactMessage = contactMessageRepository.save(contactMessage);

        ContactMessageResponseDTO responseDTO = mapper.mapContactMessageToResponseDTO(savedContactMessage);

        return ResponseMessage.<ContactMessageResponseDTO>builder()
                .message("Contact messages saved successfully!")
                .httpStatus(HttpStatus.CREATED)
                .object(responseDTO)
                .build();
    }


    public Page<ContactMessageResponseDTO> getAll(int page, int size, String sortBy, Sort.Direction order) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(order, sortBy));

        //findAll'a kadar olan kisim bize Page<ContactMessage> dondurur.

        return contactMessageRepository.findAll(pageable).map(mapper::mapContactMessageToResponseDTO);
    }


    public Page<ContactMessageResponseDTO> searchByEmail(String email, int page, int size,
                                                         String sortBy,Sort.Direction order) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(order, sortBy));

        return contactMessageRepository.findAllByEmail(email, pageable).map(mapper::mapContactMessageToResponseDTO);
    }





}