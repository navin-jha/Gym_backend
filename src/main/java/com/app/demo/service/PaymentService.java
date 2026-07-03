package com.app.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.model.Payment;
import com.app.demo.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // ✅ Save Payment + Auto Invoice + Auto Status
    public Payment savePayment(Payment payment) {

        // 🔹 Generate Invoice Number
        payment.setInvoiceNumber("INV-" + System.currentTimeMillis());

        // 🔹 Auto Status Logic
        if (payment.getDueDate() != null &&
                payment.getDueDate().isBefore(LocalDate.now())) {
            payment.setStatus("Pending");
        } else {
            payment.setStatus("Paid");
        }

        return paymentRepository.save(payment);
    }

    // ✅ Get All Payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // ✅ Get Payments by Member (for history filter)
    public List<Payment> getPaymentsByMember(Long memberId) {
        return paymentRepository.findByMemberId(memberId);
    }

    // ✅ Get Single Payment
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // ✅ Delete Payment
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}