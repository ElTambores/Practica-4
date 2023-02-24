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
        String slut = title + categories.size();
        while (DuplicatedSlut(categories, slut)) {
            Random random = new Random();
            slut+=random.nextInt(100);
        }
        return slut;
    }

    private boolean DuplicatedSlut(List<Category> categories, String slut) {
        for (Category category : categories) {
            if (category.getSlug().equals(slut)) return true;
        }
        return false;
    }
}
