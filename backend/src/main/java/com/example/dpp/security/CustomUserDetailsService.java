package com.example.dpp.security;

import com.example.dpp.services.IUserService;
import com.example.dpp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserService userService;

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

        if (user.isMfaEnabled()) {

            ServletRequestAttributes attr = (ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attr.getRequest();
            HttpSession session = request.getSession(true);
            String totpCode = request.getParameter("totpCode");
            if (totpCode == null || totpCode.isEmpty()) {
                throw new BadCredentialsException("TOTP code is required");
            }

            if (!userService.verifyTotp(user.getId(), totpCode)) {
                throw new BadCredentialsException("Invalid TOTP code");
            }

            session.setAttribute("TOTP_VERIFIED", true);
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

