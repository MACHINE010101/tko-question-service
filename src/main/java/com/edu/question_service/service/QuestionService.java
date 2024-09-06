package com.edu.question_service.service;


import com.edu.question_service.dao.QuestionDao;
import com.edu.question_service.model.Question;
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
        return  questionDao.findByCategory(category);
    }

    public Integer addQuestion(Question question) {
        return questionDao.save(question).getId();
    }
}
