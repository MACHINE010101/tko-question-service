package com.edu.question_service.controller;


import com.edu.question_service.model.Question;
import com.edu.question_service.model.QuestionWrapper;
import com.edu.question_service.model.Response;
import com.edu.question_service.service.QuestionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
@Validated
@Slf4j
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        log.info("GET /question/allQuestions - Request received");
        List<Question> questions = questionService.getAllQuestions();
        log.info("GET /question/allQuestions - Returning {} questions", questions.size());
        return ResponseEntity.ok(questions);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(
            @PathVariable @NotBlank(message = "Category cannot be blank") String category){
        log.info("GET /question/category/{} - Request received", category);
        List<Question> questions = questionService.getQuestionsByCategory(category);
        
        if(questions.isEmpty()){
            log.warn("GET /question/category/{} - No questions found", category);
        }
        
        log.info("GET /question/category/{} - Returning {} questions", category, questions.size());
        return ResponseEntity.ok(questions);
    }

    @PostMapping("add")
    public ResponseEntity<Integer> addQuestion(@Valid @RequestBody Question question){
        log.info("POST /question/add - Request received for question: {}", question.getQuestionTitle());
        Integer id = questionService.addQuestion(question);
        log.info("POST /question/add - Question created with ID: {}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(
            @RequestParam @NotBlank(message = "Category name is required") String categoryName, 
            @RequestParam @Min(value = 1, message = "Number of questions must be at least 1") Integer numQuestions){
        log.info("GET /question/generate - Generating quiz with {} questions from category: {}", numQuestions, categoryName);
        List<Integer> questionsIds = questionService.getQuestionsForQuiz(categoryName, numQuestions);
        log.info("GET /question/generate - Generated {} question IDs", questionsIds.size());
        return ResponseEntity.ok(questionsIds);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionsIds){
        log.info("POST /question/getQuestions - Request received for {} question IDs", questionsIds.size());
        List<QuestionWrapper> questions = questionService.getQuestionsFromIds(questionsIds);
        log.info("POST /question/getQuestions - Returning {} questions", questions.size());
        return ResponseEntity.ok(questions);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@Valid @RequestBody List<Response> responses){
        log.info("POST /question/getScore - Calculating score for {} responses", responses.size());
        Integer score = questionService.getScore(responses);
        log.info("POST /question/getScore - Score calculated: {}", score);
        return ResponseEntity.ok(score);
    }
}