package com.example.dpp.controller;

import com.example.dpp.model.api.auth.*;
import com.example.dpp.model.db.auth.User;
import com.example.dpp.security.TotpUtil;
import com.example.dpp.services.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private static final String ISSUER = "Shop Login :)";

    private final IUserService service;

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public UserController(IUserService service,
                          AuthenticationManager authenticationManager,
                          SecurityContextRepository securityContextRepository) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("")
    public List<UserInfo> findAll() {
        return service.getUsers();
    }

    @GetMapping("/{id}")
    public UserInfo findById(@PathVariable Integer id) {
        var user = service.getUserInfo(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        return user;
    }


//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("")
//    public void create(@RequestBody RegisterUser user) {
//        repository.save(new User(user));
//    }

    @PutMapping("/{id}")
    public void update(@RequestBody UserInfo user, @PathVariable Integer id) {
        if (service.getUserInfo(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        service.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials,
                                   HttpServletRequest httpRequest,
                                   HttpServletResponse httpResponse) {

        var user = service.getUserInfoByEmail(credentials.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), credentials.getPassword())
            );

            service.resetFailedLoginAttempts(user.getId());

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            securityContextRepository.saveContext(securityContext, httpRequest, httpResponse);

            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);


            return ResponseEntity.ok(user);
        } catch (AuthenticationException ex) {
            if (service.isUserLocked(user.getId()))
                return ResponseEntity.status(HttpStatus.LOCKED).body("Konto zblokowane na 15 minut");
            service.addFailedLogin(user.getId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Niepoprawne dane logowania");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Wylogowano");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser user) {

        if (service.userExistsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email już istnieje");
        }

        if (service.userExistsByUserName(user.getUserName())) {
            return ResponseEntity.badRequest().body("Nazwa użytkownika już istnieje");
        }

        service.register(user);
        return ResponseEntity.ok("Zarejestrowano pomyślnie");
    }

    @PostMapping("/role")
    public ResponseEntity<?> addRole(@RequestBody RoleAssignment role) {
        if (!service.userExistsById(role.getId())) {
            return ResponseEntity.badRequest().body("Użytkownik nie istnieje");
        }
        service.setRole(role);
        return ResponseEntity.ok("Rola ustawiona");
    }

    @PostMapping("/setup")
    public ResponseEntity<?> setupTotp(Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TotpSetupResponse(false, "Użytkownik nie znaleziony", null, null));
        }
        if (user.isMfaEnabled()) {
            return ResponseEntity.badRequest()
                    .body(new TotpSetupResponse(false, "Uwierzytelnianie dwuskładnikowe jest już aktywne", null, null));
        }

        String secret = service.generateMfaSecret(user.getId());

        String uri = TotpUtil.generateTotpUri(ISSUER, username, secret);

        String qrCode = TotpUtil.generateQrCode(uri);
        return ResponseEntity.ok(new TotpSetupResponse(
                true,
                "Zeskanuj kod QR w aplikacji uwierzytelniającej",
                qrCode,
                secret
        ));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyTotp(
            @RequestBody TotpVerificationRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Użytkownik nie znaleziony", null));
        }
        boolean verified = service.verifyAndEnableMfa(user.getId(),
                request.getTotpCode());
        if (verified) {
            return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Weryfikacja 2FA zakończona powodzeniem. Uwierzytelnianie dwuskładnikowe zostało aktywowane.",
                    username
            ));
        } else {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(
                            false,
                            "Nieprawidłowy kod weryfikacyjny",
                            username
                    ));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getTotpStatus(Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Użytkownik nie znaleziony", null));
        }
        return ResponseEntity.ok(new AuthResponse(
                true,
                user.isMfaEnabled() ?
                        "Uwierzytelnianie dwuskładnikowe jest aktywne" :
                        "Uwierzytelnianie dwuskładnikowe nie jest aktywne",
                username
        ));
    }

    @PostMapping("/disable")
    public ResponseEntity<?> disableTotp(Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Użytkownik nie znaleziony", null));
        }
        if (!user.isMfaEnabled()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Uwierzytelnianie dwuskładnikowe nie jest aktywne", username));
        }
        boolean disabled = service.disableMfa(user.getId());
        if (disabled) {
            return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Uwierzytelnianie dwuskładnikowe zostało dezaktywowane",
                    username
            ));
        } else {
            return ResponseEntity.internalServerError()
                    .body(new AuthResponse(
                            false,
                            "Błąd podczas dezaktywacji uwierzytelniania dwuskładnikowego",
                            username
                    ));
        }
    }

    @PostMapping("/setup")
    public ResponseEntity<?> setupTotp(Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new TotpSetupResponse(false, "Użytkownik nie znaleziony", null, null));
        }
        if (user.isMfaEnabled()) {
            return ResponseEntity.badRequest()
                    .body(new TotpSetupResponse(false, "Uwierzytelnianie dwuskładnikowe jest już aktywne", null, null));
        }

        String secret = service.generateMfaSecret(user.getId());

        String uri = TotpUtil.generateTotpUri(ISSUER, username, secret);

        String qrCode = TotpUtil.generateQrCode(uri);
        return ResponseEntity.ok(new TotpSetupResponse(
                true,
                "Zeskanuj kod QR w aplikacji uwierzytelniającej",
                qrCode,
                secret
        ));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyTotp(
            @RequestBody TotpVerificationRequest request,
            Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Użytkownik nie znaleziony", null));
        }
        boolean verified = service.verifyAndEnableMfa(user.getId(),
                request.getTotpCode());
        if (verified) {
            return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Weryfikacja 2FA zakończona powodzeniem. Uwierzytelnianie dwuskładnikowe zostało aktywowane.",
                    username
            ));
        } else {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(
                            false,
                            "Nieprawidłowy kod weryfikacyjny",
                            username
                    ));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getTotpStatus(Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Użytkownik nie znaleziony", null));
        }
        return ResponseEntity.ok(new AuthResponse(
                true,
                user.isMfaEnabled() ?
                        "Uwierzytelnianie dwuskładnikowe jest aktywne" :
                        "Uwierzytelnianie dwuskładnikowe nie jest aktywne",
                username
        ));
    }

    @PostMapping("/disable")
    public ResponseEntity<?> disableTotp(Authentication authentication) {
        String username = authentication.getName();
        User user = service.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Użytkownik nie znaleziony", null));
        }
        if (!user.isMfaEnabled()) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse(false, "Uwierzytelnianie dwuskładnikowe nie jest aktywne", username));
        }
        boolean disabled = service.disableMfa(user.getId());
        if (disabled) {
            return ResponseEntity.ok(new AuthResponse(
                    true,
                    "Uwierzytelnianie dwuskładnikowe zostało dezaktywowane",
                    username
            ));
        } else {
            return ResponseEntity.internalServerError()
                    .body(new AuthResponse(
                            false,
                            "Błąd podczas dezaktywacji uwierzytelniania dwuskładnikowego",
                            username
                    ));
        }
    }
}

