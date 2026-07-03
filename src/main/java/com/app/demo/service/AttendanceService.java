package com.app.demo.service;

import com.app.demo.model.Attendance;
import com.app.demo.model.Status;
import com.app.demo.repository.AttendanceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // ✅ Check-In
    public Attendance checkIn(Long userId) {

        LocalDate today = LocalDate.now();

        Optional<Attendance> existing = attendanceRepository.findByUserIdAndDate(userId, today);

        if (existing.isPresent()) {
            throw new RuntimeException("Already checked in");
        }

        Attendance att = new Attendance();
        att.setUserId(userId);
        att.setDate(today);
        att.setCheckInTime(LocalDateTime.now());
        att.setStatus(Status.PRESENT);

        return attendanceRepository.save(att);
    }

    // ✅ Check-Out
    public Attendance checkOut(Long userId) {

        LocalDate today = LocalDate.now();

        Attendance att = attendanceRepository
                .findByUserIdAndDate(userId, today)
                .orElseThrow(() -> new RuntimeException("Check-in not found"));

        if (att.getCheckOutTime() != null) {
            throw new RuntimeException("Already checked out");
        }

        att.setCheckOutTime(LocalDateTime.now());

        return attendanceRepository.save(att);
    }

    // ✅ Daily Report
    public List<Attendance> getDailyAttendance(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    // ✅ Monthly Report
    public List<Attendance> getMonthlyAttendance(int month, int year) {

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return attendanceRepository.findByDateBetween(start, end);
    }
}