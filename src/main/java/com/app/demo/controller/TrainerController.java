package com.app.demo.controller;

import com.app.demo.model.Trainer;
import com.app.demo.model.Member;
import com.app.demo.repository.TrainerRepository;
import com.app.demo.repository.MemberRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainerController {

    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;

    public TrainerController(TrainerRepository trainerRepository,
            MemberRepository memberRepository) {
        this.trainerRepository = trainerRepository;
        this.memberRepository = memberRepository;
    }

    // ================= ADD TRAINER =================

    @PostMapping("/add")
    public ResponseEntity<?> addTrainer(@RequestBody Trainer trainer) {

        trainerRepository.save(trainer);

        return ResponseEntity.ok(trainer);
    }

    // ================= GET ALL TRAINERS =================

    @GetMapping("/all")
    public ResponseEntity<List<Trainer>> getAllTrainers() {

        return ResponseEntity.ok(trainerRepository.findAll());
    }

    // ================= GET TRAINER BY ID =================

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainerById(@PathVariable Long id) {

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        return ResponseEntity.ok(trainer);
    }

    // ================= UPDATE TRAINER =================

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTrainer(@PathVariable Long id,
            @RequestBody Trainer updatedTrainer) {

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainer.setFullName(updatedTrainer.getFullName());
        trainer.setPhone(updatedTrainer.getPhone());
        trainer.setEmail(updatedTrainer.getEmail());
        trainer.setSpecialization(updatedTrainer.getSpecialization());
        trainer.setSalary(updatedTrainer.getSalary());

        trainerRepository.save(trainer);

        return ResponseEntity.ok(trainer);
    }

    // ================= DELETE TRAINER =================

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTrainer(@PathVariable Long id) {

        if (!trainerRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Trainer not found");
        }

        trainerRepository.deleteById(id);

        return ResponseEntity.ok("Trainer deleted successfully");
    }

    // ================= GET TRAINER MEMBERS =================

    @GetMapping("/members/{trainerId}")
    public ResponseEntity<List<Member>> getTrainerMembers(@PathVariable Long trainerId) {

        List<Member> members = memberRepository.findByTrainerTrainerId(trainerId);

        return ResponseEntity.ok(members);
    }

    // ================= SEARCH TRAINER BY NAME =================

    @GetMapping("/search")
    public ResponseEntity<List<Trainer>> searchTrainerByName(@RequestParam String name) {

        List<Trainer> trainers = trainerRepository.findByFullNameContainingIgnoreCase(name);

        return ResponseEntity.ok(trainers);

    }
}