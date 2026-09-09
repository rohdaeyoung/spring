package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 컨트롤러 어노테이션 명시
public class DemoController {

    @GetMapping("/hello") // 전송 방식 GET
    public String hello(Model model) {
        model.addAttribute("data", "반갑습니다."); // model 설정
        return "hello"; // hello.html 연결
    }

    @GetMapping("/hello2") // 2주차 연습문제 : URL 매핑 추가
    public String hello2(Model model) {
        model.addAttribute("name", "홍길동님.");      // 속성 1
        model.addAttribute("greeting", "방갑습니다."); // 속성 2
        model.addAttribute("day", "오늘.");           // 속성 3
        model.addAttribute("weather", "날씨는.");      // 속성 4
        model.addAttribute("comment", "매우 좋습니다."); // 속성 5
        return "hello2"; // hello2.html 연결
    }
}
