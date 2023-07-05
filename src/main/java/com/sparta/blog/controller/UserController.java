package com.sparta.blog.controller;

import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseBody
    @GetMapping
    public String home() {
        return "home";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> sign(@RequestBody @Valid SignupRequestDto signupRequestDto, BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body("다시 입력해주세요");
        }
        String message = userService.signup(signupRequestDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

