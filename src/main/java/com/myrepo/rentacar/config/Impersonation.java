package com.myrepo.rentacar.config;

import com.myrepo.rentacar.entities.RentalUser;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Data
@RequiredArgsConstructor
public class Impersonation implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private String secret;
    private String role;

    public static Impersonation fromUser(RentalUser user) {

        Impersonation impersonation = new Impersonation();

        impersonation.setEmail(user.getEmail());
        impersonation.setId(user.getId());
        impersonation.setPassword(user.getPassword());
        impersonation.setRole(user.getRole());

        return impersonation;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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


    public boolean isAdmin() {
        return this.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("admin"));
    }
}
