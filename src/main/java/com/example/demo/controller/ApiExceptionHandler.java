package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// [7주차 연습문제 ②] 오류 처리 한 곳에 모으기
// 등록·수정·삭제마다 반복되던 try-catch 를 여기 한 곳에서 처리한다.
@RestControllerAdvice(assignableTypes = {MemberApiController.class, AdminApiController.class})
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class) // 중복 아이디, 빈 값, 허용 안 되는 권한, 없는 회원 …
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // 400
    }
}
