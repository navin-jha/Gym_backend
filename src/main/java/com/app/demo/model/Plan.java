package com.app.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @Column(name = "duration_months", nullable = false)
    private Integer durationMonths;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "admission_fee")
    private BigDecimal admissionFee;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "active")
    private Boolean active = true;

    // ===== AUTO CALCULATE FINAL PRICE =====

    @PrePersist
    @PreUpdate
    public void calculateFinalPrice() {

        if (price != null && discount != null) {

            BigDecimal discountAmount = price.multiply(discount).divide(BigDecimal.valueOf(100));

            this.finalPrice = price.subtract(discountAmount);

        } else {
            this.finalPrice = price;
        }
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(Integer durationMonths) {
        this.durationMonths = durationMonths;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(BigDecimal admissionFee) {
        this.admissionFee = admissionFee;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }
}