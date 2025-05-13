package com.tpe.student_management.contact_us.repository;

import com.tpe.student_management.contact_us.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

Page<ContactMessage> findAllByEmail(String email, Pageable pageable);

//findByEmail
    //findByEmailEquals
    //findAllByEmailEquals
    //Bu dort isim de bire bir ayni isi yapacak.



}
