package org.example.travelplanner.service;

import org.example.travelplanner.dto.CategoryDTO;
import org.example.travelplanner.entity.Attraction;
import org.example.travelplanner.entity.Category;
import org.example.travelplanner.repository.AttractionRepository;
import org.example.travelplanner.repository.CategoryRepository;
import org.example.travelplanner.repository.PlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AttractionRepository attractionRepository;
    private final PlanRepository planRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           AttractionRepository attractionRepository,
                           PlanRepository planRepository) {
        this.categoryRepository = categoryRepository;
        this.attractionRepository = attractionRepository;
        this.planRepository = planRepository;
    }

    private CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }

    private Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(int id) {
        return categoryRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public CategoryDTO createCategory(CategoryDTO dto) {
        if (categoryRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }
        Category category = categoryRepository.save(toEntity(dto));
        return toDTO(category);
    }

    public CategoryDTO updateCategory(int id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.findByName(dto.getName())
                .filter(c -> c.getId() != id)
                .ifPresent(c -> {
                    throw new RuntimeException("Category with this name already exists");
                });
        category.setName(dto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return toDTO(updatedCategory);
    }

    public void deleteCategory(int id) {
        List<Attraction> attractions = attractionRepository.findByCategoryId(id);
        for (Attraction attraction : attractions) {
            if (!planRepository.findByAttractionId(attraction.getId()).isEmpty()) {
                throw new RuntimeException("Cannot delete category: contains attractions used in plans");
            }
        }
        attractionRepository.deleteAll(attractions);
        categoryRepository.deleteById(id);
    }
}