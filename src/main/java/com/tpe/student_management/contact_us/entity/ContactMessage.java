package com.tpe.student_management.contact_us.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data //Bütün lombok anotasyonlarini icerir
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true) //
public class ContactMessage {

    @Id //Primary key anlamina gelir
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String subject;

    @NotNull
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "US")
    private LocalDateTime createdAt;





}


/*
@Data anotasyonu, Project Lombok kütüphanesine ait bir anotasyondur ve Java sınıflarındaki boilerplate (tekrar eden)
 kodları otomatik olarak üretir.


✅ @Data Nedir ve Ne İşe Yarar?
@Data, aşağıdaki anotasyonların birleşimidir:
@Getter → Tüm alanlar için getter metodları
@Setter → Tüm alanlar için setter metodları
@ToString → toString() metodu
@EqualsAndHashCode → equals() ve hashCode() metodu
@RequiredArgsConstructor → final alanlar için constructor

⚠️ Dikkat!
@Data tüm alanlara setter ve getter ekler. Eğer bazı alanlar sadece okunabilir olmalıysa (final vs), @Getter veya @Setter
gibi daha özelleştirilmiş anotasyonlar tercih etmelisin.
@Data, Spring entity'lerinde doğrudan kullanıldığında bazı durumlarda (özellikle toString() içinde ilişkiler varsa)
sonsuz döngü (StackOverflow) oluşturabilir. Bu yüzden ilişkili entity'lerde dikkatli kullanılır veya @ToString.Exclude
gibi ek anotasyonlarla desteklenir.

@Builder anotasyonu, Lombok kütüphanesine ait bir özelliktir ve Builder Design Pattern'ı kullanarak nesne oluşturmayı
kolaylaştırır. Özellikle çok alanlı sınıflarda nesne oluşturmayı daha okunabilir, güvenli ve esnek hale getirir.
✅ @Builder Ne İşe Yarar?
new anahtar kelimesi ile constructor kullanmak yerine, alanları isimleriyle sırayla belirtmeden nesne üretmeni sağlar.

Özellikle parametre sayısı fazla olan sınıflarda kullanışlıdır.
toBuilder = true eklendiğinde, var olan bir nesneden kopya çıkarabilir ve bazı alanları değiştirebilirsin.

 */
