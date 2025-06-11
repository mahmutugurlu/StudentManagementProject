package com.tpe.student_management.entity.classes.user;

import com.tpe.student_management.entity.enums.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "t_role")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private Role role;

    private String roleName;
}
