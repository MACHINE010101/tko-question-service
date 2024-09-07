package com.edu.question_service.controller;


import com.edu.question_service.exception.QuestionNotFoundException;
import com.edu.question_service.model.Question;
import com.edu.question_service.model.QuestionWrapper;
import com.edu.question_service.model.Response;
import com.edu.question_service.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor()
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        try{
            List<Question> questions = questionService.getAllQuestions();
            return ResponseEntity.ok(questions);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        try{
            List<Question> questions = questionService.getQuestionsByCategory(category);
            if(questions.isEmpty()){
                throw new QuestionNotFoundException("No questions found for category " + category);
            }
            return ResponseEntity.ok(questions);
        } catch (QuestionNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @PostMapping("add")
    public ResponseEntity<Integer> addQuestion(@RequestBody Question question){
        try{
            Integer id = questionService.addQuestion(question);
            return ResponseEntity.ok(id);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions){
        try{
            List<Integer> questionsIds = questionService.getQuestionsForQuiz(categoryName, numQuestions);
            return ResponseEntity.ok(questionsIds);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> questionsIds){
        try{
            List<QuestionWrapper> questions = questionService.getQuestionsFromIds(questionsIds);
            return ResponseEntity.ok(questions);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        try{
            Integer score = questionService.getScore(responses);
            return ResponseEntity.ok(score);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", e);
        }
    }
}