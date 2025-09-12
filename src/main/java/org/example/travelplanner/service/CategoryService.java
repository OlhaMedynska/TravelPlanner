package org.example.travelplanner.service;

import org.example.travelplanner.dto.CategoryDTO;
import org.example.travelplanner.entity.Category;
import org.example.travelplanner.exception.ResourceNotFoundException;
import org.example.travelplanner.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
        Category category = categoryRepository.save(toEntity(dto));
        return toDTO(category);
    }

    public CategoryDTO updateCategory(int id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        category.setName(dto.getName());
        Category updatedCategory = categoryRepository.save(toEntity(dto));
        return toDTO(updatedCategory);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
