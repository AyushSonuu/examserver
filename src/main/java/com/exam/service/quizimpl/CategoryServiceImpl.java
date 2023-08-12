package com.exam.service.quizimpl;

import com.exam.model.quiz.Category;
import com.exam.repo.quiz.CategoryRepository;
import com.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Set<Category> getCategories() {
        return new LinkedHashSet<>(this.categoryRepository.findAll());
    }

    @Override
    public Category getCategory(Long categoryid) {
        return this.categoryRepository.findById(categoryid).get();
    }

    @Override
    public void  deleteCategory(Long categoryid) {
        Category category =new Category();
        category.setCid(categoryid);
        this.categoryRepository.delete(category);

    }
}
