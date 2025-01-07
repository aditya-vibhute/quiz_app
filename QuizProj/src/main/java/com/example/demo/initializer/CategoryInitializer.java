package com.example.demo.initializer;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CategoryRepository;
import com.example.demo.dao.QuestionDao;
import com.example.demo.model.Category;

import jakarta.annotation.PostConstruct;

@Component
public class CategoryInitializer {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionDao questionDao;

    @PostConstruct
    public void init() {
        List<String> predefinedCategories = Arrays.asList("Java,Python");

        for (String categoryName : predefinedCategories) {
            // Find the category by name, or create a new one if it doesn't exist
            Category category = categoryRepository.findByName(categoryName)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(categoryName);
                        return categoryRepository.save(newCategory);
                    });

            // Set the initial question count based on the number of questions in this category
            int initialCount = questionDao.countByCategory(categoryName);
            category.setQuestionCount(initialCount);
            categoryRepository.save(category); // Save the updated count
        }
    }
}

