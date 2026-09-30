package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 컨트롤러 어노테이션 명시
public class MemberController {

    @GetMapping("/login") // 로그인 화면 (POST /login 은 시큐리티가 처리)
    public String login() {
        return "login"; // login.html 연결
    }

    @GetMapping("/signup") // 회원가입 화면
    public String signupForm() {
        return "signup"; // signup.html 연결
    }
}
