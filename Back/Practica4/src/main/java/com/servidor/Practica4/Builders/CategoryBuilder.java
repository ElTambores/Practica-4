package com.servidor.Practica4.Builders;

import com.servidor.Practica4.Forms.CategoryForm;
import com.servidor.Practica4.Models.Category;

import java.util.Random;

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
}
