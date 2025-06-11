package com.tpe.student_management.contact_us.repository;

import com.tpe.student_management.contact_us.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    Page<ContactMessage> findAllByEmail(String email, Pageable pageable);
    //findByEmail
    //findByEmailEquals
    //findAllByEmailEquals
    //Bu dort isim de bire bir ayni isi yapacak.

    Page<ContactMessage>findAllBySubject(String subject, Pageable pageable);

    @Query("select c from ContactMessage c where FUNCTION('DATE', c.createdAt) between ?1 and ?2")
    List<ContactMessage> findMessagesBetweenDates(LocalDate beginDate, LocalDate endDate);
}
