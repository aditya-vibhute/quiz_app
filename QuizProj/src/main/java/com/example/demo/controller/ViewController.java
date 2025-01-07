package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    // Display login page
    @GetMapping("/")
    public String showLoginPage(HttpSession session) {
        // Check if user is already logged in
        if (session.getAttribute("user") != null) {
            return "redirect:/index";  // Redirect to index page if logged in
        }
        return "login";  // Render login.html
    }

    // Handle login form submission
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        // Example hardcoded credentials
        String validUsername = "admin";
        String validPassword = "password123";

        // Validate credentials
        if (username.equals(validUsername) && password.equals(validPassword)) {
            session.setAttribute("user", username);  // Store user in session
            return "redirect:/index";  // Redirect to index page
        } else {
            model.addAttribute("error", "Invalid username or password!");
            return "login";  // Stay on login page
        }
    }

    // Display the index page after login
    @GetMapping("/index")
    public String showLandingPage(HttpSession session, Model model) {
        // Check if user is logged in
        String user = (String) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";  // Redirect to login page if not logged in
        }
        model.addAttribute("username", user);  // Pass username to view
        return "index";  // Render index.html
    }

    // Logout endpoint
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidate the session
        return "redirect:/";  // Redirect to login page
    }

    @GetMapping("/questionsPage")
    public String showQuestionsPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";  // Redirect to login page if not logged in
        }
        return "questions";  // Render questions.html
    }

    @GetMapping("/quizPage")
    public String showQuizPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";  // Redirect to login page if not logged in
        }
        return "quiz";  // Render quiz.html
    }

    @GetMapping("/createQuiz")
    public String createQuiz(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/";  // Redirect to login page if not logged in
        }
        return "createQuiz";
    }

    @GetMapping("/add-question")
    public String showAddQuestionPage(HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            return "redirect:/";  // Redirect to login page if not logged in
        }
        return "add-question";  // Render add-question.html
    }


}
