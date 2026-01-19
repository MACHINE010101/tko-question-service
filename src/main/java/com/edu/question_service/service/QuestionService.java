package com.edu.question_service.service;


import com.edu.question_service.dao.QuestionDao;
import com.edu.question_service.exception.QuestionNotFoundException;
import com.edu.question_service.model.Question;
import com.edu.question_service.model.QuestionWrapper;
import com.edu.question_service.model.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionDao questionDao;

    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        log.info("Fetching all questions");
        List<Question> questions = questionDao.findAll();
        log.info("Retrieved {} questions", questions.size());
        return questions;
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsByCategory(String category) {
        log.info("Fetching questions for category: {}", category);
        List<Question> questions = questionDao.findByCategory(category);
        log.info("Retrieved {} questions for category: {}", questions.size(), category);
        return questions;
    }

    @Transactional
    public Integer addQuestion(Question question) {
        log.info("Adding new question with title: {}", question.getQuestionTitle());
        Question savedQuestion = questionDao.save(question);
        log.info("Successfully added question with ID: {}", savedQuestion.getId());
        return savedQuestion.getId();
    }

    @Transactional(readOnly = true)
    public List<Integer> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        log.info("Generating {} random questions for category: {}", numQuestions, categoryName);
        List<Integer> questionIds = questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
        log.info("Generated {} question IDs for quiz", questionIds.size());
        return questionIds;
    }

    @Transactional(readOnly = true)
    public List<QuestionWrapper> getQuestionsFromIds(List<Integer> questionsIds) {
        log.info("Fetching questions for {} IDs", questionsIds.size());
        List<Question> questions = questionDao.findAllById(questionsIds);

        List<QuestionWrapper> wrappers = questions.stream()
                .map(question -> new QuestionWrapper(
                        question.getId(),
                        question.getQuestionTitle(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4()
                )).toList();
        
        log.info("Successfully wrapped {} questions", wrappers.size());
        return wrappers;
    }

    @Transactional(readOnly = true)
    public Integer getScore(List<Response> responses) {
        log.info("Calculating score for {} responses", responses.size());
        Integer score = 0;

        for (Response response : responses) {
            Question question = questionDao.findById(response.getId())
                    .orElseThrow(() -> {
                        log.error("Question not found with ID: {}", response.getId());
                        return new QuestionNotFoundException("Question not found with ID: " + response.getId());
                    });
            
            if(response.getResponse().equals(question.getRightAnswer())) {
                score++;
            }
        }

        log.info("Calculated score: {}/{}", score, responses.size());
        return score;
    }
}
