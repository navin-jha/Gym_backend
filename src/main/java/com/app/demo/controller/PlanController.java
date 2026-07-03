package com.app.demo.controller;

import com.app.demo.model.Plan;
import com.app.demo.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@CrossOrigin(origins = "http://localhost:5173")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    // CREATE PLAN
    @PostMapping
    public Plan createPlan(@RequestBody Plan plan) {
        System.out.println("CREATE PLAN API HIT");
        return planService.addPlan(plan);
    }

    // GET ALL PLANS
    @GetMapping
    public List<Plan> getAllPlans() {
        return planService.getAllPlans();
    }

    // GET PLAN BY ID
    @GetMapping("/{planId}")
    public Plan getPlanById(@PathVariable Long planId) {
        return planService.getPlanById(planId);
    }

    // UPDATE PLAN
    @PutMapping("/{planId}")
    public Plan updatePlan(@PathVariable Long planId, @RequestBody Plan plan) {
        return planService.updatePlan(planId, plan);
    }

    // DELETE PLAN
    @DeleteMapping("/{planId}")
    public String deletePlan(@PathVariable Long planId) {
        planService.deletePlan(planId);
        return "Plan deleted successfully";
    }

    // DEACTIVATE PLAN
    @PutMapping("/deactivate/{planId}")
    public Plan deactivatePlan(@PathVariable Long planId) {
        return planService.deactivatePlan(planId);
    }

    // ACTIVATE PLAN
    @PutMapping("/activate/{planId}")
    public Plan activatePlan(@PathVariable Long planId) {
        return planService.activatePlan(planId);
    }
}