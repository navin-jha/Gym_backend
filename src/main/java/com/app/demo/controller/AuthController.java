package com.app.demo.controller;

import com.app.demo.model.User;
import com.app.demo.repository.UserRepository;
import com.app.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtUtil jwtUtil;

        public AuthController(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil) {
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtUtil = jwtUtil;
        }

        // REGISTER
        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody User user) {

                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

                return ResponseEntity.ok(Map.of(
                                "success", true,
                                "token", token,
                                "role", user.getRole().name(),
                                "name", user.getName(), // ⭐ IMPORTANT
                                "email", user.getEmail()));
        }

        // LOGIN
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody User request) {

                Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

                if (userOpt.isEmpty()) {
                        return ResponseEntity.status(401)
                                        .body(Map.of("success", false, "message", "User not found"));
                }

                User user = userOpt.get();

                if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        return ResponseEntity.status(401)
                                        .body(Map.of("success", false, "message", "Invalid credentials"));
                }

                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

                return ResponseEntity.ok(
                                Map.of(
                                                "success", true,
                                                "token", token,
                                                "role", user.getRole().name(),
                                                "name", user.getName(), // ⭐ IMPORTANT
                                                "email", user.getEmail()));
        }
}