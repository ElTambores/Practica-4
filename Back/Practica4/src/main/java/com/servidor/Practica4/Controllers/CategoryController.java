package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Forms.CategoryForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @CrossOrigin
    @GetMapping("/categories")
    public List<Map<String, Object>> getCategories() {
        return categoryService.getAllCategories();
    }

    @CrossOrigin
    @PostMapping("/categories")
    public Map<String, Object> createCategory(@RequestBody CategoryForm categoryForm, HttpServletRequest request) {
        return categoryService.createCategory(categoryForm, request.getAttribute("user"));
    }

    @CrossOrigin
    @GetMapping("/categories/{slug}")
    public Map<String, Object> getCategory(@PathVariable String slug) {
        return categoryService.getCategory(slug);
    }

    @CrossOrigin
    @PutMapping("/categories/{slug}")
    public Map<String, Object> updateCategory(@PathVariable String slug, @RequestBody CategoryForm categoryForm, HttpServletRequest request) {
        return categoryService.updateCategory(slug, categoryForm, request.getAttribute("user"));
    }

    @CrossOrigin
    @DeleteMapping("/categories/{slug}")
    public Object deleteCategory(@PathVariable String slug, HttpServletRequest request){
        return categoryService.deleteCategory(slug, request.getAttribute("user"));
    }
}
