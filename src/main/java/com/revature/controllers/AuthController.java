package com.revature.controllers;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.exceptions.EmailReservedException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.AuthService;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000","http://ec2-52-33-155-232.us-west-2.compute.amazonaws.com:4200", "http://52.33.155.232:4200"}, allowCredentials = "true")
@AllowSysOut
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            User user = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
            session.setAttribute("user", user);

            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute("user");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest) {
        try {
            User created = authService.register(new User(0,
                    registerRequest.getEmail(),
                    registerRequest.getPassword(),
                    registerRequest.getFirstName(),
                    registerRequest.getLastName())
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (EmailReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
