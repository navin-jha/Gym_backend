package com.app.demo.service;

import com.app.demo.model.Plan;
import com.app.demo.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    // ✅ ADD PLAN
    public Plan addPlan(Plan plan) {

        LocalDate startDate = LocalDate.now();
        plan.setStartDate(startDate);

        if (plan.getDurationMonths() != null) {
            plan.setEndDate(startDate.plusMonths(plan.getDurationMonths()));
        }

        plan.setPaymentDate(LocalDate.now());

        if (plan.getDiscount() != null) {

            BigDecimal discountAmount = plan.getPrice()
                    .multiply(plan.getDiscount())
                    .divide(BigDecimal.valueOf(100));

            plan.setFinalPrice(plan.getPrice().subtract(discountAmount));

        } else {
            plan.setFinalPrice(plan.getPrice());
        }

        // 🔥 IMPORTANT FIX (ACTIVE HANDLE)
        if (plan.getActive() == null) {
            plan.setActive(true); // default true
        }

        return planRepository.save(plan);
    }

    // ✅ GET ALL
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    // ✅ GET BY ID
    public Plan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    // ✅ UPDATE PLAN
    public Plan updatePlan(Long id, Plan updatedPlan) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setPlanName(updatedPlan.getPlanName());
        plan.setPrice(updatedPlan.getPrice());
        plan.setDiscount(updatedPlan.getDiscount());
        plan.setDurationMonths(updatedPlan.getDurationMonths());
        plan.setAdmissionFee(updatedPlan.getAdmissionFee());
        plan.setPaymentMethod(updatedPlan.getPaymentMethod());

        // 🔥 ACTIVE UPDATE FIX
        if (updatedPlan.getActive() != null) {
            plan.setActive(updatedPlan.getActive());
        }

        if (updatedPlan.getDurationMonths() != null) {
            plan.setEndDate(plan.getStartDate().plusMonths(updatedPlan.getDurationMonths()));
        }

        if (updatedPlan.getDiscount() != null) {

            BigDecimal discountAmount = plan.getPrice()
                    .multiply(plan.getDiscount())
                    .divide(BigDecimal.valueOf(100));

            plan.setFinalPrice(plan.getPrice().subtract(discountAmount));

        } else {
            plan.setFinalPrice(plan.getPrice());
        }

        return planRepository.save(plan);
    }

    // ✅ DELETE
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

    // ✅ DEACTIVATE
    public Plan deactivatePlan(Long id) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setActive(false);

        return planRepository.save(plan);
    }

    // ✅ ACTIVATE
    public Plan activatePlan(Long id) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // ✅ NEW (correct)
        if (plan.getActive() == null) {
            plan.setActive(true); // default
        }

        return planRepository.save(plan);
    }
}