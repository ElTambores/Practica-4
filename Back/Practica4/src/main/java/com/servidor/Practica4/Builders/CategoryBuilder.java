package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.CategoryForm;
import com.servidor.Practica4.Models.Category;

import java.util.*;

public class CategoryBuilder {

    public Category fromForm(CategoryForm categoryForm, String slut) {
        Category category = new Category();

        category.setTitle(categoryForm.getTitle());
        category.setDescription(categoryForm.getDescription());
        category.set__v(0);
        category.setColor(getRandomColor());
        category.setSlug(slut);
        category.setModerators("");

        return category;
    }

    private String getRandomColor() {
        Random random = new Random();
        int tone = (int) (random.nextFloat() * 256);
        return "hsl(" + tone + ", 50%, 50%)";
    }

    public Map<String, Object> getJson(Category category) {
        Map<String, Object> json = new HashMap<>();
        List<String> moderators = new ArrayList<>();

        json.put("color", category.getColor());
        json.put("description", category.getDescription());
        json.put("moderators", moderators);
        json.put("slug", category.getSlug());
        json.put("title", category.getTitle());
        json.put("__v", category.get__v());
        json.put("_id", category.get_id());

        return json;
    }

    public List<Map<String, Object>> jsonFromList(List<Category> categories) {
        List<Map<String, Object>> categoryList = new ArrayList<>();
        for (Category category : categories) {
            categoryList.add(getJson(category));
        }
        return categoryList;
    }
}
