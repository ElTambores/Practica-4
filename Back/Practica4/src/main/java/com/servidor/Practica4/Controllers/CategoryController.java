package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Forms.CategoryForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @CrossOrigin
    @GetMapping("/categories")
    public List<Category> getCategories(HttpServletResponse response) throws NoSuchAlgorithmException {
        return categoryService.getAllCategories();
    }

    @CrossOrigin
    @PostMapping("/categories")
    public Category createCategory(@RequestBody CategoryForm categoryForm, HttpServletResponse response) throws NoSuchAlgorithmException {
        return categoryService.createCategory(categoryForm);
    }

    @CrossOrigin
    @GetMapping("categories/{slug}")
    public Category getCategory(@PathVariable String slug, HttpServletResponse response) throws NoSuchAlgorithmException {
        return categoryService.getAllCategory(slug);
    }

}
