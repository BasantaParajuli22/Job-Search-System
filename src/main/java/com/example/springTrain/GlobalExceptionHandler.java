//package com.example.springTrain;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception ex, Model model) {
//        model.addAttribute("errorMessage", "Something went wrong. Please try again later.");
//        return "error";  // Return a custom error view
//    }
//}
