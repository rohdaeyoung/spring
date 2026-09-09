# SPRING

자바웹프로그래밍(2) 실습 저장소

- **개발환경** : VS Code / Java 25 / Spring Boot 4.1.1 / Maven
- **템플릿 엔진** : Thymeleaf
- **실행** : `./mvnw spring-boot:run` → http://localhost:8080

---

## 2주차 수업 내용

**스프링 부트 개발환경 설정 및 테스트 완료**

### 1. 개발 환경 설정
- VS Code 확장 모듈 설치
  - Extension Pack for Java
  - Spring Boot Extension Pack
- JDK 25 설치 및 `java.jdt.ls.java.home` / `spring-boot.ls.java.home` 경로 설정
- Spring Initializr로 Maven 프로젝트 생성 (패키징 Jar, 자바 25)

### 2. 프로젝트 폴더 구조 (Maven)
```
spring
├── src/main/java/com/example/demo   # 루트 패키지
│   ├── DemoApplication.java         # @SpringBootApplication (프로그램 시작점)
│   └── DemoController.java          # @Controller (URL 매핑)
├── src/main/resources
│   ├── static                       # css, 이미지, js
│   └── templates                    # Thymeleaf 템플릿
├── src/test                         # 테스트 코드
└── pom.xml                          # 빌드 및 종속성 설정
```

### 3. 기본 화면 실행
- `templates/index.html` 생성 → 메인 페이지 출력
- 디버깅 없이 실행(Ctrl + F5) → 내장 Tomcat 8080 포트 구동
- DB 관련 모듈(`spring-boot-starter-data-jpa`, `mysql-connector-j`) 주석 처리
  - DB 설정 전이라 실행 시 오류 발생하므로 해제

### 4. 스프링 부트 동작 과정
- 내장 **Tomcat**(WAS) 위에서 동작하며 스프링 컨테이너가 객체를 관리(DI)
- **DispatcherServlet**(프론트 컨트롤러)이 요청/응답을 처리
  1. `/이름` 형태로 URI 요청
  2. 컨트롤러와 모델을 통해 정보 전달
  3. Thymeleaf가 서버 측에서 HTML 렌더링 응답
- **MVC 디자인 패턴** : Model(정보 저장) / View(화면 구성) / Controller(흐름 제어)

### 5. Thymeleaf
- HTML 기반 템플릿 엔진, MVC에서 View(V) 역할
- 선언 : `<html xmlns:th="http://www.thymeleaf.org">`
- 텍스트 출력 : `<span th:text="${data}"></span>`
- 컨트롤러가 리턴한 `viewName`과 `templates/viewName.html`이 연결됨

### 6. URL 매핑과 컨트롤러
- `DemoController.java` 생성 후 `@GetMapping("/hello")` 매핑
- `model.addAttribute("data", "반갑습니다.")` 로 모델 설정 후 `"hello"` 리턴

| URL | 컨트롤러 메서드 | 연결 화면 | 출력 |
|---|---|---|---|
| `/` | (정적 연결) | `index.html` | 안녕하세요! |
| `/hello` | `hello()` | `hello.html` | 안녕하세요! / 반갑습니다. |

---

## 2주차 과제 내용

**URL 매핑과 컨트롤러 추가하기**

### 요구사항
1. 기존 `hello.html` 링크를 수정 (링크명 : 두번째 헬로 페이지 / URL 매핑 : `/hello2`)
2. 컨트롤러에 `hello2` 메서드를 작성하고 **5개의 속성**을 추가
3. `hello2.html`을 작성하여 5개 속성 변수를 출력

### 구현 내용

`DemoController.java` — `/hello2` 매핑 추가
```java
@GetMapping("/hello2")
public String hello2(Model model) {
    model.addAttribute("name", "홍길동님.");        // 속성 1
    model.addAttribute("greeting", "방갑습니다.");   // 속성 2
    model.addAttribute("day", "오늘.");             // 속성 3
    model.addAttribute("weather", "날씨는.");        // 속성 4
    model.addAttribute("comment", "매우 좋습니다.");  // 속성 5
    return "hello2";
}
```

`hello2.html` — `th:text`로 5개 속성 출력
```html
<h1>안녕하세요!</h1>
<p th:text="${name}"></p>
<p th:text="${greeting}"></p>
<p th:text="${day}"></p>
<p th:text="${weather}"></p>
<p th:text="${comment}"></p>
<a href="/">홈페이지 메인</a>
```

### 실행 결과

| URL | 출력 화면 |
|---|---|
| `/hello` | 안녕하세요! / 반갑습니다. / [두번째 헬로 페이지] |
| `/hello2` | 안녕하세요! / 홍길동님. / 방갑습니다. / 오늘. / 날씨는. / 매우 좋습니다. / [홈페이지 메인] |

화면 이동 흐름 : `/` → `/hello` → `/hello2` → `/`
