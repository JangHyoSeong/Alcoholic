// User.java
package com.e206.alcoholic.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 사용자 식별자

    @Column(nullable = false, unique = true)
    private String username; // 사용자 아이디

    @Column(nullable = false)
    private String nickname; // 사용자 닉네임

    @Builder
    public User(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    } // 닉네임 수정
}