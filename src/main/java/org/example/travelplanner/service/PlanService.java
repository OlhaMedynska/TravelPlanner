package org.example.travelplanner.service;

import org.example.travelplanner.Attraction;
import org.example.travelplanner.Plan;
import org.example.travelplanner.User;
import org.example.travelplanner.dto.PlanDTO;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.PlanRepository;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final AttractionRepository attractionRepository;

    public PlanService(PlanRepository planRepository, UserRepository userRepository, AttractionRepository attractionRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.attractionRepository = attractionRepository;
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public Plan getPlanById(int id) {
        return planRepository.findById(id).orElse(null);
    }

    public Plan createPlan(PlanDTO dto) {
        Plan plan = new Plan();

        plan.setName(dto.getName());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        plan.setComment(dto.getComment());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(dto.getAttractionId()).orElseThrow(()->new RuntimeException("Attraction is not found"));

        plan.setUser(user);
        plan.setAttractions(attraction);


        return planRepository.save(plan);
    }

    public Plan updatePlan(int id, PlanDTO dto) {
        Plan plan = planRepository.findById(id).orElseThrow(()->new RuntimeException("Plan not found"));

        plan.setName(dto.getName());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        Attraction attraction = attractionRepository.findById(dto.getAttractionId()).orElseThrow(()->new RuntimeException("Attraction is not found"));

        plan.setUser(user);
        plan.setAttractions(attraction);

        return planRepository.save(plan);
    }

    public void deletePlan(int id) {
        planRepository.deleteById(id);
    }
}
