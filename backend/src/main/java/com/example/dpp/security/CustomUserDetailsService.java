package com.example.dpp.security;

import com.example.dpp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        var user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " +
                    username);
        }

        if (user.isAccountLocked()) {
            throw new LockedException("Konto zablokowane. Spróbuj ponownie później.");
        }

        List<SimpleGrantedAuthority> authorities =
                new ArrayList<SimpleGrantedAuthority>(Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword().getHash(),
                authorities
        );
    }
}

