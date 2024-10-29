// Auth.java
package com.e206.alcoholic.global.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 사용자 식별자

    @Column(nullable = false, unique = true)
    private String username; // 사용자 아이디

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호

    @Column(nullable = false)
    private String nickname; // 닉네임

    @Builder
    public Auth(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}