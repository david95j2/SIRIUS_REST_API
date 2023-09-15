package com.example.sirius_restapi.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "login_id")
    private String loginId;
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "user_regdate")
    private LocalDateTime regdate;

    private String authority;

    @PrePersist
    public void prePersist() {
        this.regdate = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}