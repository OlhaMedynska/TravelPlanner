package org.example.travelplanner.controller;

import jakarta.validation.Valid;
import org.example.travelplanner.entity.Plan;
import org.example.travelplanner.dto.PlanDTO;
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
    public Plan getById(@PathVariable int id) {
        return planService.getPlanById(id);
    }

    @PostMapping
    public Plan create(@RequestBody @Valid PlanDTO dto) {
        return planService.createPlan(dto);
    }

    @PutMapping("/{id}")
    public Plan update(@PathVariable int id, @RequestBody @Valid PlanDTO dto) {
        return planService.updatePlan(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        planService.deletePlan(id);
    }
}
