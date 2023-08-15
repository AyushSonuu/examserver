package com.exam.controller;

import com.exam.model.quiz.Category;
import com.exam.model.quiz.Quiz;
import com.exam.model.user.CustomResponse;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/quiz")
public class QuizController {
    
    @Autowired
    private QuizService quizService;
    
    @PostMapping("/")
    public ResponseEntity<CustomResponse> add(@RequestBody Quiz quiz){
        Quiz quiz1 = this.quizService.addQuiz(quiz);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Quiz Added",quiz1));
    }

    @PutMapping("/")
    public ResponseEntity<CustomResponse> updateQuiz(@RequestBody Quiz quiz){
        Quiz quiz1 = this.quizService.addQuiz(quiz);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Quiz Updated",quiz1));
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse> getQuizes(){
        Set<Quiz> quizzes = this.quizService.getQuizzes();
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Quizzes Fetched",quizzes));
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<CustomResponse> getQuiz(@PathVariable("quizId") Long quizId){
        Quiz quiz = this.quizService.getQuiz(quizId);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Quiz Fetched id : "+quizId,quiz));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<CustomResponse> deleteQuiz(@PathVariable("quizId") Long quizId){
        this.quizService.deleteQuiz(quizId);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Quiz Deleted",null));
    }

    @GetMapping("/category/{cid}")
    public ResponseEntity<CustomResponse> getQuizzesByCategory(@PathVariable("cid") Long cid){
        Category category =new Category();
        category.setCid(cid);
        List<Quiz> quizzesOfCategory = this.quizService.getQuizzesOfCategory(category);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"quizzes by category",quizzesOfCategory));
    }
    @GetMapping("/active")
    public ResponseEntity<CustomResponse> getActiveQuizzes(@PathVariable("cid") Long cid){
        Category category = new Category();
        category.setCid(cid);
        List<Quiz> activeQuizzesOfCategory = this.quizService.getActiveQuizzesOfCategory(category);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"quizzes by category",activeQuizzesOfCategory));
    }

    @GetMapping("/category/active/{cid}")
    public ResponseEntity<CustomResponse> getActiveQuizzesByCategory(){
        List<Quiz> activeQuizzes = this.quizService.getActiveQuizzes();
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"quizzes by category",activeQuizzes));
    }

}
