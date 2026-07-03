package com.app.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.demo.model.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Trainer findByEmail(String email);
    
    List<Trainer> findByFullNameContainingIgnoreCase(String fullName);

}