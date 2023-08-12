package com.exam.service;

import com.exam.model.quiz.Category;

import java.util.Set;

public interface CategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public Set<Category> getCategories();
    public Category getCategory(Long categoryid);
    public void deleteCategory(Long categoryid);
}
