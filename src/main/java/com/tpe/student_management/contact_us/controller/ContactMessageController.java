package com.tpe.student_management.contact_us.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.student_management.contact_us.dto.ContactMessageRequestDTO;
import com.tpe.student_management.contact_us.dto.ContactMessageResponseDTO;
import com.tpe.student_management.contact_us.service.ContactMessageService;
import com.tpe.student_management.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact-messages")
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
        ContactMessageService contactMessageService = null;
        return contactMessageService.searchByEmail(email, page, size, sortBy, order);
    }


    @GetMapping("/search-by-subject")
    public Page<ContactMessageResponseDTO> searchBySubject(
            @RequestParam String subject,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "ASC")Sort.Direction order
    ){
        return contactMessageService.searchBySubject(subject,page,size,sortBy,order);
    }


    @GetMapping("/id/{id}")
    public ResponseMessage<ContactMessageResponseDTO> findById(@PathVariable("id") Long id){


        return contactMessageService.findById(id);
    }


    @DeleteMapping("/deleteid/{id}")


    public ResponseMessage<ContactMessageResponseDTO> deleteById(@PathVariable("id") Long id) {


        return contactMessageService.deleteById(id);


    }


    @DeleteMapping("/id")
    public ResponseMessage<ContactMessageResponseDTO> deleteByIdQuery(@RequestParam("id") Long id) {

        return contactMessageService.deleteById(id);
    }


    @GetMapping("/id")
    public ResponseMessage<ContactMessageResponseDTO> findByIdUsingQueryParameter(@RequestParam("id") Long id){


        return contactMessageService.findById(id);
    }


    @GetMapping("/search-by-date")
    public Page<ContactMessageResponseDTO> searchByDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return contactMessageService.searchByDateBetween(startDate, endDate, page, size, sortBy, order);
    }




    }






    // -********************* searchBySubject ********************
    // ******************** searchByDateBetween ******************** //Bu zor olan
    // -******************** deleteById ********************
    // ******************** deleteByIdUsingQueryParameter ******************** //Normalde tercih edilmez
    //- ******************** findById ********************
    // ******************** findByIdUsingQueryParameter ******************** //Tercih edilmez




