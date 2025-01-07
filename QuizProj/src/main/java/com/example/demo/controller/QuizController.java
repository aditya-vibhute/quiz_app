package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.ui.Model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.CategoryRepository;
import com.example.demo.model.Category;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;
import com.example.demo.model.QuizDTO;
import com.example.demo.model.Response;
import com.example.demo.service.QuizService;

//import ch.qos.logback.core.model.Model;
@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
    }
    
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @GetMapping("getAll")
    public List<QuizDTO> getAll(){
        ResponseEntity<List<Quiz>> quizzesAll= quizService.getAll();
        List<Quiz> quizzes=quizzesAll.getBody();
        return quizzes.stream().map(quiz -> new QuizDTO(
                quiz.getId(), // Pass ID here
                quiz.getTitle(),
                quiz.getQuestions().isEmpty() ? "N/A" : quiz.getQuestions().get(0).getCategory(),
                quiz.getQuestions().size()
        )).collect(Collectors.toList());
    }


    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }

        @GetMapping("/allQuizzes")
        public ResponseEntity<List<Map<String, Object>>> getAllQuizNames() {
            ResponseEntity<List<Quiz>> quizzesResponse = quizService.getAll();
            List<Quiz> quizzes=quizzesResponse.getBody();
            List<Map<String, Object>> quizData = quizzes.stream()
                .map(quiz -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", quiz.getId());
                    map.put("title", quiz.getTitle());
                    return map;
                }).collect(Collectors.toList());

            return ResponseEntity.ok(quizData);
        }

        @GetMapping("/start/{quizId}")
        public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer quizId) {
            return quizService.getQuizQuestions(quizId);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<String> deleteQuiz(@PathVariable int id) {
            try {
                boolean isDeleted = quizService.deleteQuiz(id);
                if (isDeleted) {
                    return new ResponseEntity<>("Quiz deleted successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Failed to delete quiz", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        

}
