package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.CategoryBuilder;
import com.servidor.Practica4.Forms.CategoryForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Repos.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CategoryService {
    CategoryRepo categoryRepo;
    CategoryBuilder categoryBuilder = new CategoryBuilder();

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public Category createCategory(CategoryForm categoryForm) {
        Category category = categoryBuilder.fromForm(categoryForm, generateSlug(categoryForm.getTitle()));
        return categoryRepo.save(category);
    }

    private String generateSlug(String title) {
        List<Category> categories = getAllCategories();
        while (DuplicatedSlut(categories, title)) {
            Random random = new Random();
            title += random.nextInt(100);
        }
        return title;
    }

    private boolean DuplicatedSlut(List<Category> categories, String slug) {
        for (Category category : categories) {
            if (category.getSlug().equals(slug)) return true;
        }
        return false;
    }

    public Category getAllCategory(String slug) {
        List<Category> categories = categoryRepo.findBySlugEquals(slug);
        return categories.size() == 0 ? null : categories.get(0);
    }
}
