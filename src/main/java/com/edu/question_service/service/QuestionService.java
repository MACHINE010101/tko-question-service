package com.edu.question_service.service;


import com.edu.question_service.dao.QuestionDao;
import com.edu.question_service.model.Question;
import com.edu.question_service.model.QuestionWrapper;
import com.edu.question_service.model.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;

    public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public Integer addQuestion(Question question) {
        return questionDao.save(question).getId();
    }

    public List<Integer> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        return questionDao.findRandomQuestionsByCategory(categoryName, numQuestions);
    }

    public List<QuestionWrapper> getQuestionsFromIds(List<Integer> questionsIds) {

        List<Question> questions = questionDao.findAllById(questionsIds);

        return questions.stream()
                .map(question -> new QuestionWrapper(
                        question.getId(),
                        question.getQuestionTitle(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4()
                )).toList();
    }

    public Integer getScore(List<Response> responses) {
        Integer score = 0;

        for (Response response : responses) {
            String answer = questionDao.findById(response.getId()).get().getRightAnswer();
            if(response.getResponse().equals(answer)) {
                score++;
            }
        }

        return score;
    }
}
