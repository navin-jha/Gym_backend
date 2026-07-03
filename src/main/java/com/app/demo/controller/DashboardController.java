// package com.app.demo.controller;

// import com.app.demo.dto.DashboardStatsDTO;
// import com.app.demo.service.MemberService;
// import com.app.demo.service.StaffService;
// import com.app.demo.service.TrainerService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/dashboard")
// @CrossOrigin(origins = "http://localhost:5173")
// @PreAuthorize("hasRole('ADMIN')")  // 🔒 Sirf ADMIN access
// public class DashboardController {

//     @Autowired
//     private MemberService memberService;

//     @Autowired
//     private TrainerService trainerService;

//     @Autowired
//     private StaffService staffService;

//     @GetMapping("/stats")
//     public DashboardStatsDTO getDashboardStats() {

//         long totalMembers = memberService.countAllMembers();
//         long activeMembers = memberService.countActiveMembers();
//         long totalTrainers = trainerService.countAllTrainers();
//         long totalStaff = staffService.countAllStaff();

//         // Admin info (yaha hardcode, ya logged-in user fetch kar sakte ho)
//         String email = "admin@gym.com";
//         String name = "Admin";

//         return new DashboardStatsDTO(email, name, totalMembers, activeMembers, totalTrainers, totalStaff);
//     }
// }