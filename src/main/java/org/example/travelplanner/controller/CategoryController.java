package org.example.travelplanner.controller;

import jakarta.validation.Valid;
import org.example.travelplanner.dto.CategoryDTO;
import org.example.travelplanner.service.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(@PathVariable int id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO dto) {
        try {
            return ResponseEntity.ok(categoryService.createCategory(dto));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Category with this name already exists");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid CategoryDTO dto) {
        try {
            return ResponseEntity.ok(categoryService.updateCategory(id, dto));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Category with this name already exists");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}