package com.sparta.blog.controller;

import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        String message = userService.signup(signupRequestDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/signup/secureadmin")
    public ResponseEntity<String> signupAdmin(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        signupRequestDto.setAdmin(true);
        String message = userService.signup(signupRequestDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

