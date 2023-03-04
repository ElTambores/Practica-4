package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.CategoryBuilder;
import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Exceptions.ErrorUpdatingException;
import com.servidor.Practica4.Forms.CategoryForm;
import com.servidor.Practica4.Models.Category;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.CategoryRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CategoryService {
    CategoryRepo categoryRepo;

    CategoryBuilder categoryBuilder = new CategoryBuilder();
    UserBuilder userBuilder = new UserBuilder();

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Map<String, Object>> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categoryBuilder.jsonFromList(categories);
    }

    public Map<String, Object> createCategory(CategoryForm categoryForm, Object userInfo) {
        if (userInfo == null) return null;
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        if (!user.getRole().equals("admin")) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Category category = categoryBuilder.fromForm(categoryForm, generateSlug(categoryForm.getTitle()));
        return categoryBuilder.getJson(categoryRepo.save(category));
    }

    private String generateSlug(String title) {
        List<Category> categories = categoryRepo.findBySlugEquals(title);
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

    public Map<String, Object> getCategory(String slug) {
        List<Category> categories = categoryRepo.findBySlugEquals(slug);
        return categories.size() == 0 ? null : categoryBuilder.getJson(categories.get(0));
    }

    public Map<String, Object> updateCategory(String slug, CategoryForm categoryForm, Object userInfo) {
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        if (!user.getRole().equals("admin")) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        try {
            categoryRepo.update(slug, categoryForm.getTitle(), categoryForm.getDescription());
            List<Category> categories = categoryRepo.findBySlugEquals(slug);
            if (categories.size() == 0) throw new ErrorUpdatingException();
            return categoryBuilder.getJson(categories.get(0));
        } catch (Exception e) {
            throw new ErrorUpdatingException();
        }
    }

    public Object deleteCategory(String slug, Object userInfo) {
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        if (!user.getRole().equals("admin")) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        long deletedRows = categoryRepo.deleteBySlug(slug);

        return deletedRows == 1;
    }

}
