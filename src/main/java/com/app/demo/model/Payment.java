package com.app.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Plan plan;

    private double amountPaid;
    private String paymentMode;
    private LocalDate paymentDate;
    private LocalDate dueDate;
    private String status;
    private String invoiceNumber;


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getPaymentId() {
        return paymentId;
    }
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public Plan getPlan() {
        return plan;
    }
    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    public double getAmountPaid() {
        return amountPaid;
    }
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}