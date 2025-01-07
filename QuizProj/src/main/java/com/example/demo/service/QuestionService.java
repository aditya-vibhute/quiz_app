package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CategoryRepository;
import com.example.demo.dao.QuestionDao;
import com.example.demo.model.Category;
import com.example.demo.model.Question;

@Service
public class QuestionService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    QuestionDao questionDao;
    
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);  // Save the question to the database
            questionDao.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for visibility
            return new ResponseEntity<>("Failed to add question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean deleteQuestion(int id) {
        try {
            if (questionDao.existsById(id)) {  // Check if question exists
                Question question = questionDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

                questionDao.deleteById(id);   // Delete the question by its ID
                return true;  // Return true if deletion was successful
            } else {
                return false;  // Return false if the question was not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if an exception occurred
        }
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        if (questionDao.existsById(question.getId())) {
            questionDao.save(question);
            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }
    
    public ResponseEntity<Question> getQuestionById(int id) {
        try {
            // Check if the question exists in the database
            Question question = questionDao.findById(id).orElse(null);
            if (question == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if not found
            }
            return new ResponseEntity<>(question, HttpStatus.OK);  // Return 200 with the question data
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Return 500 in case of an error
        }
    }
    
    
    
}
