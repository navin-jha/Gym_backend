package com.app.demo.controller;

import com.app.demo.dto.DashboardStatsDTO;
import com.app.demo.model.User;
import com.app.demo.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Overview
    @GetMapping("/overview")
    public ResponseEntity<?> getAdminOverview(Authentication authentication) {

        String loggedInEmail = authentication.getName();

        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        DashboardStatsDTO dto = new DashboardStatsDTO();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());

        return ResponseEntity.ok(dto);
    }

    // ✅ Upload Profile Image
    @PostMapping("/upload-profile")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("profileImage") MultipartFile file,
            Authentication authentication) {

        try {

            String email = authentication.getName();

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            user.setProfileImage(file.getBytes());

            userRepository.save(user);

            return ResponseEntity.ok("Profile image uploaded");

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body("Upload failed");

        }
    }
    
    @GetMapping("/profile-image")
    public ResponseEntity<byte[]> getProfileImage(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "image/jpeg")
                .body(user.getProfileImage());
    }
}