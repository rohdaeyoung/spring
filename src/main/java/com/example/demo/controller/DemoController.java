package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.domain.TestDB; // 도메인 연동
import com.example.demo.model.service.TestService; // 최상단 서비스 클래스 연동 추가

@Controller // 컨트롤러 어노테이션 명시
public class DemoController {

    @Autowired
    TestService testService; // DemoController 클래스 아래 객체 주입

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

    @GetMapping("/testdb") // 4주차 : 데이터베이스 테스트 페이지
    public String getAllTestDBs(Model model) {
        // TestDB test = testService.findByName("홍길동"); // 1명만 조회하던 기존 코드
        // model.addAttribute("data4", test);

        List<TestDB> users = testService.findAll(); // 다수 사용자 조회
        model.addAttribute("users", users);
        System.out.println("데이터 출력 디버그 : " + users);
        return "testdb"; // testdb.html 연결
    }
}
