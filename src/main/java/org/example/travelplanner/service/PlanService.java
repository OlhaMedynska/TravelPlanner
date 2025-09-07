package org.example.travelplanner.service;

import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Plan;
import org.example.travelplanner.entity.User;
import org.example.travelplanner.dto.PlanDTO;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.PlanRepository;
import org.example.travelplanner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<PlanDTO> getAllPlans() {
        return planRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PlanDTO getPlanById(int id) {
        return planRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public PlanDTO createPlan(PlanDTO dto) {
        Plan saved = planRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public PlanDTO updatePlan(int id, PlanDTO dto) {
        Plan plan = planRepository.findById(id).orElseThrow(()->new RuntimeException("Plan not found"));

        plan.setName(dto.getName());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        plan.setComment(dto.getComment());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));

        plan.setUser(user);

        if(dto.getAttractionIds() != null & !dto.getAttractionIds().isEmpty()) {
            Set<Attraction> attractions = dto.getAttractionIds().stream()
                    .map(attractionId -> attractionRepository.findById(attractionId)
                    .orElseThrow(() -> new RuntimeException("Attraction not found")))
                    .collect(Collectors.toSet());
            plan.setAttractions(attractions);
        }

        Plan saved = planRepository.save(plan);
        return toDTO(saved);
    }

    public void deletePlan(int id) {
        planRepository.deleteById(id);
    }

    private PlanDTO toDTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setName(plan.getName());
        dto.setStartDate(plan.getStartDate());
        dto.setEndDate(plan.getEndDate());
        dto.setComment(plan.getComment());
        dto.setUserId(plan.getUser().getId());

        if(plan.getAttractions() != null && !plan.getAttractions().isEmpty()) {
            dto.setAttractionIds(plan.getAttractions().stream()
                    .map(a -> a.getId())
                    .collect(Collectors.toSet())
            );
        }
        return dto;
    }

    private Plan toEntity(PlanDTO dto) {
        Plan plan = new Plan();
        plan.setName(dto.getName());
        plan.setStartDate(dto.getStartDate());
        plan.setEndDate(dto.getEndDate());
        plan.setComment(dto.getComment());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
        plan.setUser(user);

        if(dto.getAttractionIds() != null && !dto.getAttractionIds().isEmpty()) {
            Set<Attraction> attractions = dto.getAttractionIds().stream()
                    .map(id -> attractionRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Attraction not found")))
                    .collect(Collectors.toSet());
            plan.setAttractions(attractions);
        }
        return plan;
    }
}
