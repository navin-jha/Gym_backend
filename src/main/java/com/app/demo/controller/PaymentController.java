package com.app.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.demo.model.Payment;
import com.app.demo.service.PaymentService;

@RestController

@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        try {
            return ResponseEntity.ok(paymentService.savePayment(payment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving payment");
        }
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<List<Payment>> getPaymentsByMember(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentsByMember(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Payment Deleted");
    }
}