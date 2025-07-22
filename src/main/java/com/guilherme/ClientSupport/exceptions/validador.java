package com.guilherme.ClientSupport.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class validador {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException exception) 
    {
        List<String> erros = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(erro -> erro.getDefaultMessage())
            .collect(Collectors.toList());

        Map<String, Object> corpo = new HashMap<>();
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("errors", erros);
        corpo.put("timestamp", LocalDateTime.now());

        return ResponseEntity.badRequest().body(corpo);
    }

     @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> UsernameNotFound(UsernameNotFoundException exception) 
    {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("errors", exception.getMessage());
        corpo.put("timestamp", LocalDateTime.now());

        return ResponseEntity.badRequest().body(corpo);
    }
}

    

