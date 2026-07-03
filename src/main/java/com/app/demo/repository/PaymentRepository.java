package com.app.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.demo.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByMemberId(Long memberId);
}