package com.jvls.financialcontrol.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usr")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Column(name = "name", length = 255, nullable = false)
    @Size(min = 4, max = 255)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "password", length = 255, nullable = false)
    @Size(min = 8, max = 255)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    @Size(min = 8, max = 255)
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
