package com.app.demo.controller;

import com.app.demo.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "http://localhost:5173")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // ✅ Check-In
    @PostMapping("/check-in")
    public ResponseEntity<?> checkIn(@RequestParam Long userId) {
        return ResponseEntity.ok(attendanceService.checkIn(userId));
    }

    // ✅ Check-Out
    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut(@RequestParam Long userId) {
        return ResponseEntity.ok(attendanceService.checkOut(userId));
    }

    // ✅ Daily Report
    @GetMapping("/daily")
    public ResponseEntity<?> getDailyAttendance(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return ResponseEntity.ok(attendanceService.getDailyAttendance(parsedDate));
    }

    // ✅ Monthly Report
    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyAttendance(
            @RequestParam int month,
            @RequestParam int year) {

        return ResponseEntity.ok(
                attendanceService.getMonthlyAttendance(month, year));
    }
}
