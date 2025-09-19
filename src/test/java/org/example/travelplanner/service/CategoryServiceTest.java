package org.example.travelplanner.service;

import org.example.travelplanner.dto.CategoryDTO;
import org.example.travelplanner.entity.Category;
import org.example.travelplanner.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_ShouldReturnList() {
        Category category = new Category();
        category.setId(1);
        category.setName("Food");
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        List<CategoryDTO> result = categoryService.getAllCategories();
        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).getName());
    }

    @Test
    void getCategoryById_ShouldReturnCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Food");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        CategoryDTO dto = categoryService.getCategoryById(1);
        assertNotNull(dto);
        assertEquals("Food", dto.getName());
    }

    @Test
    void createCategory_ShouldReturnSavedCategory() {
        Category category = new Category();
        category.setId(1);
        category.setName("Food");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Food");
        CategoryDTO result = categoryService.createCategory(dto);
        assertEquals("Food", result.getName());
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategory() {
        Category existing = new Category();
        existing.setId(1);
        existing.setName("Food");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArgument(0));
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Drinks");
        CategoryDTO result = categoryService.updateCategory(1, dto);
        assertEquals("Drinks", result.getName());
    }

    @Test
    void deleteCategory_ShouldCallRepository() {
        doNothing().when(categoryRepository).deleteById(1);
        categoryService.deleteCategory(1);
        verify(categoryRepository, times(1)).deleteById(1);
    }
}