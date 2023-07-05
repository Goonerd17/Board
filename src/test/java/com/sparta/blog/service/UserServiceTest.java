package com.sparta.blog.service;

import com.sparta.blog.dto.SignupRequestDto;
import com.sparta.blog.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @DisplayName("Signup Test")
    @Test
    public void signupTest() {

        SignupRequestDto test1 = new SignupRequestDto();
        test1.setUsername("username17");
        test1.setPassword("Password17!");

        SignupRequestDto test2 = new SignupRequestDto();
        test2.setUsername("username17");
        test2.setPassword("qlalFqjs17!");

        String result1 = userService.signup(test1);

        assertThat(result1).isEqualTo("회원가입 성공");
        assertThrows(IllegalArgumentException.class, () -> { userService.signup(test2); });
    }
}