package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Forms.CategoryForm;
import com.servidor.Practica4.Services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Map<String, Object>> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories")
    public Map<String, Object> createCategory(@RequestBody CategoryForm categoryForm, HttpServletRequest request) {
        return categoryService.createCategory(categoryForm, request.getAttribute("user"));
    }

    @GetMapping("/categories/{slug}")
    public Map<String, Object> getCategory(@PathVariable String slug) {
        return categoryService.getCategory(slug);
    }

    @PutMapping("/categories/{slug}")
    public Map<String, Object> updateCategory(@PathVariable String slug, @RequestBody CategoryForm categoryForm, HttpServletRequest request) {
        return categoryService.updateCategory(slug, categoryForm, request.getAttribute("user"));
    }

    @DeleteMapping("/categories/{slug}")
    public Boolean deleteCategory(@PathVariable String slug, HttpServletRequest request) {
        return categoryService.deleteCategory(slug, request.getAttribute("user"));
    }
}
