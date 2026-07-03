package com.app.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.app.demo.model.Trainer;
import com.app.demo.repository.TrainerRepository;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }
}