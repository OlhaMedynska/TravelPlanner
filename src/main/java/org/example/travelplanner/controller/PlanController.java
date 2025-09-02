package org.example.travelplanner.controller;

import org.example.travelplanner.entity.Plan;
import org.example.travelplanner.service.PlanService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/plans")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping
    public List<Plan> getAll() {
        return planService.getAllPlans();
    }

    @GetMapping("/{id}")
    public Plan getById(@PathVariable Long id) {
        return planService.getPlanById(id);
    }

    @PostMapping
    public Plan create(@RequestBody Plan plan) {
        return planService.createPlan(plan);
    }

    @PutMapping("/{id}")
    public Plan update(@PathVariable Long id, @RequestBody Plan plan) {
        return planService.updatePlan(id, plan);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        planService.deletePlan(id);
    }
}
