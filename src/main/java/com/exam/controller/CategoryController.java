package com.exam.controller;

import com.exam.model.quiz.Category;
import com.exam.model.user.CustomResponse;
import com.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;


/**
 * CategoryController is a REST API controller that handles operations related to quiz categories.
 */
@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //add category

    /**
     * Endpoint to add a new quiz category.
     *
     * @param category The Category object representing the new category.
     * @return A ResponseEntity with a CustomResponse indicating the success of the operation.
     */
    @PostMapping("/")
    public ResponseEntity<CustomResponse> addCategory(@RequestBody Category category){
        Category category1 = this.categoryService.addCategory(category);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Category Added",category1));

    }

    /**
     * Endpoint to retrieve a specific quiz category by its ID.
     *
     * @param categoryId The ID of the category to retrieve.
     * @return A ResponseEntity with a CustomResponse containing the retrieved category.
     */

    @GetMapping("/{categoryId}")
    public ResponseEntity<CustomResponse> getCategory(@PathVariable("categoryId") Long categoryId){
        Category category = this.categoryService.getCategory(categoryId);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"category recieved",category));
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse> getCategories(){
        Set<Category> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"category recieved",categories));
    }

    @PutMapping("/")
    public ResponseEntity<CustomResponse> updateCategory(@RequestBody Category category){
        Category category1 = this.categoryService.updateCategory(category);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"updated Category",category1));

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CustomResponse> deleteCategory(@PathVariable("categoryId") Long categoryId){
        this.categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Category Deleted",null));
    }

}
