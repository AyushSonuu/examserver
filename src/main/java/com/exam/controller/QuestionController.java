package com.exam.controller;

import com.exam.model.quiz.Question;
import com.exam.model.quiz.Quiz;
import com.exam.model.user.CustomResponse;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;


    @PostMapping("/")
    public ResponseEntity<CustomResponse> addQuestion(@RequestBody Question question){
        Question question1 = this.questionService.addQuestion(question);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Qeuestion Added",question1));
    }

    @PutMapping("/")
    public ResponseEntity<CustomResponse> updateQuestion(@RequestBody Question question){
        Question question1 = this.questionService.updateQuestion(question);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Qeuestion updated",question1));
    }

    @GetMapping("/question/{qid}")
    public ResponseEntity<CustomResponse> getQuestionsOfQuiz(@PathVariable("qid") Long qid){
        Quiz quiz = this.quizService.getQuiz(qid);
        Set<Question> questions = quiz.getQuestions();
        List<Question> questionsList = new ArrayList<>(questions);
        if(questionsList.size()>Integer.parseInt(quiz.getNumberOfQuestions())){
            questionsList = questionsList.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()));
        }
        Collections.shuffle(questionsList);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"All Qeuestions Fetched",questionsList));
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<CustomResponse> getQuestion(@PathVariable("questionId")Long questionId){
        Question question = this.questionService.getQuestion(questionId);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Qeuestion Added",question));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<CustomResponse>deleteQuestions(@PathVariable("questionId")Long questionId){
        this.questionService.deleteQuestion(questionId);
        return ResponseEntity.ok(new CustomResponse(LocalDateTime.now(),"Qeuestion Added",null));
    }

}
