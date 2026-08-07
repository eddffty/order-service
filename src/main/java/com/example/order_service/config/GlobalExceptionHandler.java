package com.example.order_service.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(Model model) {
        model.addAttribute("errorMessage", "Невозможно удалить: этот объект используется в другом месте (например, у покупателя есть заказы).");
        return "error";
    }
}