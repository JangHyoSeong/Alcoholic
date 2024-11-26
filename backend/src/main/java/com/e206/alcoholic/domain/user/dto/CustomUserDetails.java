package com.e206.alcoholic.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@ToString
public class CustomUserDetails implements UserDetails {
    private final Integer userId;
    private final String username;
    private final String password;
    private final String nickname;
    private final String role;

    @Builder
    public CustomUserDetails(Integer userId, String username, String password, String nickname, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
