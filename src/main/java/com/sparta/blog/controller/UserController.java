package com.sparta.blog.controller;

import com.sparta.blog.dto.ApiResponse;
import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.sparta.blog.utils.ResponseUtils.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String home() {
        return "home";
    }

    @PostMapping("/signup")
    public ApiResponse<?> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ok(userService.signup(signupRequestDto));
    }

    @PostMapping("/signup/secureadmin")
    public ApiResponse<?> signupAdmin(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        signupRequestDto.setAdmin(true);
        return ok(userService.signup(signupRequestDto));
    }
}

