package com.tpe.student_management.contact_us.controller;

import com.tpe.student_management.contact_us.dto.ContactMessageRequestDTO;
import com.tpe.student_management.contact_us.dto.ContactMessageResponseDTO;
import com.tpe.student_management.contact_us.entity.ContactMessage;
import com.tpe.student_management.contact_us.service.ContactMessageService;
import com.tpe.student_management.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-messages") //"/contactMessages" tercih edilmez.
public class ContactMessageController {
    private final ContactMessageService contactMessageService;

    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponseDTO> saveContactMessage(@RequestBody @Valid
                                                                             ContactMessageRequestDTO dto){
        return contactMessageService.saveContactMessage(dto);
    }

    @GetMapping("/get-all")
    public Page<ContactMessageResponseDTO> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction order){
        return contactMessageService.getAll(page, size, sortBy, order);
    }

    @GetMapping("/search-by-email")
    public Page<ContactMessageResponseDTO> searchByEmail(
            @RequestParam String email,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction order){
        return contactMessageService.searchByEmail(email, page, size, sortBy, order);
    }

    // ******************** searchBySubject ********************
    @GetMapping("/searchBySubject") //http://localhost:8080/contactMessages/searchBySubject?subject=deneme
    public Page<ContactMessageResponseDTO> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "dateTime") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type){
        return contactMessageService.searchBySubject(subject,page,size,sort,type);
    }

    // ******************** searchByDateBetween ******************** //Bu zor olan
    @GetMapping("/searchBetweenDates") //http://localhost:8080/contactMessages/searchBetweenDates?beginDate=2023-09-13&endDate=2023-09-15
    public ResponseEntity<List<ContactMessage>> searchByDateBetween(
            @RequestParam(value = "beginDate") String beginDateString,
            @RequestParam(value = "endDate") String endDateString){
        List<ContactMessage> contactMessages = contactMessageService.searchByDateBetween(beginDateString,
                endDateString);
        return ResponseEntity.ok(contactMessages);
    }

    // ******************** deleteById ********************
    @DeleteMapping("/deleteById/{contactMessageId}")//http://localhost:8080/contactMessages/deleteById/2
    public ResponseEntity<String> deleteById(@PathVariable Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }

    // ******************** deleteByIdUsingQueryParameter ******************** //Normalde tercih edilmez
    @DeleteMapping("/deleteByIdParam") //http://localhost:8080/contactMessages/deleteByIdParam?contactMessageId=1
    public ResponseEntity<String> deleteByIdUsingQueryParameter(@RequestParam(value = "contactMessageId")
                                                                    Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }

    // ******************** findById ********************
    @GetMapping("/getById/{contactMessageId}")//http://localhost:8080/contactMessages/getById/1
    public ResponseEntity<ContactMessage> findById(@PathVariable Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }
    // ******************** findByIdUsingQueryParameter ******************** //Tercih edilmez
    @GetMapping("/getByIdParam") //http://localhost:8080/contactMessages/getByIdParam?contactMessageId=1
    public ResponseEntity<ContactMessage> findByIdUsingQueryParameter(@RequestParam(value = "contactMessageId")
                                                                          Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }
}
