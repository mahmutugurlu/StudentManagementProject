package com.tpe.student_management.contact_us.service;

import com.tpe.student_management.contact_us.dto.ContactMessageRequestDTO;
import com.tpe.student_management.contact_us.dto.ContactMessageResponseDTO;
import com.tpe.student_management.contact_us.entity.ContactMessage;
import com.tpe.student_management.contact_us.mapper.ContactMessageMapper;
import com.tpe.student_management.contact_us.messages.Messages;
import com.tpe.student_management.contact_us.repository.ContactMessageRepository;
import com.tpe.student_management.exception.ConflictException;
import com.tpe.student_management.exception.ResourceNotFoundException;
import com.tpe.student_management.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

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

    //Buradan sonrasi odev
    public Page<ContactMessageResponseDTO> searchBySubject(String subject, int page, int size, String sort,
                                                          String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (Objects.equals(type, "desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findAllBySubject(subject, pageable). // Derived
                map(mapper::mapContactMessageToResponseDTO);
    }

    public List<ContactMessage> searchByDateBetween(String beginDateString, String endDateString) {
        try {
            LocalDate beginDate = LocalDate.parse(beginDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            return contactMessageRepository.findMessagesBetweenDates(beginDate, endDate);
        } catch (DateTimeParseException e) {
            throw new ConflictException(Messages.WRONG_DATE_FORMAT);
        }
    }

    public String deleteById(Long id) {
        getContactMessageById(id);
        contactMessageRepository.deleteById(id);
        return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
    }

    public ContactMessage getContactMessageById(Long id) {
        return contactMessageRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));
    }
}
