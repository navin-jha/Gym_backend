package com.app.demo.repository;

import com.app.demo.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByUserIdAndDate(Long userId, LocalDate date);

    List<Attendance> findByDate(LocalDate date);

    // ✅ Better monthly query (no DB function)
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
}