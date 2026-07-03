package com.app.demo.controller;

import com.app.demo.model.Member;
import com.app.demo.model.Trainer;
import com.app.demo.repository.MemberRepository;
import com.app.demo.repository.TrainerRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/member")
@CrossOrigin(origins = "http://localhost:5173")
public class MemberController {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberRepository memberRepository,
            TrainerRepository trainerRepository,
            PasswordEncoder passwordEncoder) {

        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ================= REGISTER MEMBER =================

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("age") Integer age,
            @RequestParam("gender") String gender,
            @RequestParam("address") String address,
            @RequestParam("password") String password,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        if (memberRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        try {
            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setPhone(phone);
            member.setAge(age);
            member.setGender(gender);
            member.setAddress(address);
            member.setPassword(passwordEncoder.encode(password));
            member.setMembershipStatus("ACTIVE");

            if (profileImage != null) {
                member.setProfileImage(profileImage.getBytes());
            }

            memberRepository.save(member);

            Map<String, Object> response = new HashMap<>();
            response.put("id", member.getId());
            response.put("name", member.getName());
            response.put("email", member.getEmail());

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving member");
        }
    }

    // ================= GET ALL MEMBERS =================

    @GetMapping("/all")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    // ================= GET LOGGED IN MEMBER =================

    @GetMapping("/info")
    public ResponseEntity<?> getMemberInfo(Authentication authentication) {

        Member member = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

        Map<String, Object> dto = new HashMap<>();

        dto.put("name", member.getName());
        dto.put("email", member.getEmail());
        dto.put("phone", member.getPhone());
        dto.put("age", member.getAge());
        dto.put("gender", member.getGender());
        dto.put("address", member.getAddress());
        dto.put("registeredAt", member.getCreatedAt());

        if (member.getProfileImage() != null) {
            dto.put("profileImage", Base64.getEncoder().encodeToString(member.getProfileImage()));
        }

        return ResponseEntity.ok(dto);
    }

    // ================= UPLOAD PROFILE IMAGE =================

    @PostMapping("/upload-profile")
    public ResponseEntity<?> uploadMemberProfile(
            @RequestParam("profileImage") MultipartFile file,
            Authentication authentication) {

        Member member = memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));

        try {
            member.setProfileImage(file.getBytes());
            memberRepository.save(member);

            return ResponseEntity.ok("Member profile image uploaded");

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }

    // ================= UPDATE MEMBER =================

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody Member updatedMember) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setName(updatedMember.getName());
        member.setPhone(updatedMember.getPhone());
        member.setAge(updatedMember.getAge());
        member.setGender(updatedMember.getGender());
        member.setAddress(updatedMember.getAddress());

        memberRepository.save(member);

        return ResponseEntity.ok(member);
    }

    // ================= DELETE MEMBER =================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {

        if (!memberRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Member not found");
        }

        memberRepository.deleteById(id);

        return ResponseEntity.ok("Member deleted successfully");
    }

    // ================= SEARCH MEMBER =================

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMember(@RequestParam String name) {

        List<Member> members = memberRepository.findByNameContainingIgnoreCase(name);

        return ResponseEntity.ok(members);
    }

    // ================= FREEZE MEMBERSHIP =================

    @PutMapping("/freeze/{id}")
    public ResponseEntity<?> freezeMembership(@PathVariable Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setMembershipStatus("FROZEN");

        memberRepository.save(member);

        return ResponseEntity.ok("Membership frozen");
    }

    // ================= RENEW MEMBERSHIP =================

    @PutMapping("/renew/{id}")
    public ResponseEntity<?> renewMembership(@PathVariable Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setMembershipStatus("ACTIVE");

        memberRepository.save(member);

        return ResponseEntity.ok("Membership renewed");
    }

    // ================= ASSIGN TRAINER =================

    @PutMapping("/assign-trainer/{memberId}/{trainerId}")
    public ResponseEntity<?> assignTrainer(@PathVariable Long memberId,
            @PathVariable Long trainerId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        member.setTrainer(trainer);

        memberRepository.save(member);

        return ResponseEntity.ok("Trainer assigned successfully");
    }
}