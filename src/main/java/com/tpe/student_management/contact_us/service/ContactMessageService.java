package com.tpe.student_management.contact_us.service;


import com.tpe.student_management.contact_us.dto.ContactMessageRequestDTO;
import com.tpe.student_management.contact_us.dto.ContactMessageResponseDTO;
import com.tpe.student_management.contact_us.entity.ContactMessage;
import com.tpe.student_management.contact_us.mapper.ContactMessageMapper;
import com.tpe.student_management.contact_us.repository.ContactMessageRepository;
import com.tpe.student_management.exception.ContactMessageNotFoundException;
import com.tpe.student_management.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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


    public Page<ContactMessageResponseDTO> searchBySubject(String subject, int page, int size, String sortBy,
                                                           Sort.Direction order) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by(order, sortBy));

        return contactMessageRepository.findAllBySubject(subject, pageable).
                map(mapper::mapContactMessageToResponseDTO);


    }


    public ResponseMessage<ContactMessageResponseDTO> findById(Long id) {

        ContactMessage byId = getById(id);

        ContactMessageResponseDTO responseDTO = mapper.mapContactMessageToResponseDTO(byId);

        return ResponseMessage.<ContactMessageResponseDTO>builder()
                .message("Contact messages found !" + id)
                .httpStatus(HttpStatus.OK)
                .object(responseDTO)
                .build();


        /*
        public ContactMessageResponseDTO findById(Long id) {
  ContactMessage contactMessage=  contactMessageRepository.findById(id).
  orElseThrow(()->new ContactMessageNotFoundException("Contact Message Not Found with id :  "+id));
  return   mapper.mapContactMessageToResponseDTO(contactMessage);
          }

         */

    }

    private ContactMessage getById(Long id) {
        return contactMessageRepository.findById(id).orElseThrow(() -> new ContactMessageNotFoundException("Contact Message Not Found with id :  " + id));
    }


    public ResponseMessage<ContactMessageResponseDTO> deleteById(Long id) {

      ContactMessage byId =  getById(id);
        contactMessageRepository.deleteById(id);

       // ContactMessageResponseDTO responseDTO = mapper.mapContactMessageToResponseDTO(byId);

        return ResponseMessage.<ContactMessageResponseDTO>builder()
                .message(" User deleted !" + id)
                .httpStatus(HttpStatus.NO_CONTENT)
               // .object(responseDTO)
                .build();


    }


    public Page<ContactMessageResponseDTO> searchByDateBetween(LocalDate startDate, LocalDate endDate,
                                                               int page, int size, String sortBy, Sort.Direction order) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order, sortBy));
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        return contactMessageRepository.findAllByCreatedAtBetween(start, end, pageable)
                .map(mapper::mapContactMessageToResponseDTO);
    }



}