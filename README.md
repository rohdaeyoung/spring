# SPRING

성결대학교 자바웹프로그래밍(2) 실습 저장소

| 항목 | 내용 |
|---|---|
| IDE | VS Code |
| 언어 / JDK | Java 25 (최소 17 이상) |
| 프레임워크 | Spring Boot 4.1.1 |
| 빌드 도구 | Maven |
| 템플릿 엔진 | Thymeleaf |
| 내장 WAS | Apache Tomcat (8080) |

실행 : `./mvnw spring-boot:run` → http://localhost:8080

---

# 2주차 수업 내용

## 1. 웹 트렌드 분석

### 스프링 프레임워크 → 스프링 부트

| 구분 | 스프링 프레임워크 (2004) | 스프링 부트 (2014) |
|---|---|---|
| 설정 | XML 기반 수동 설정 | **자동 환경설정** |
| 진입 장벽 | 매우 높음 | 낮음 |
| 서버 | 외부 WAS 별도 설치 | **WAS 내장** |
| 빌드 | 수동 | 자동 빌드 및 리로드 |

- 스프링 부트의 **핵심** : 작은 규모 및 단순 서블릿 관리
- 자바 언어로 작성되어 **JVM 위에서 실행**
- 비유 : 스프링이 "재료를 직접 손질하는 것"이라면, 스프링 부트는 "믹서기에 넣고 버튼만 누르는 것"

### 전자정부 표준 프레임워크 (eGov)

- 스프링 기반으로 2010년 이후 공개, 국내 주요 공공기관 웹 프로젝트의 표준
- 요구 기술 : 자바, JSP, jQuery, eGov (최근 스프링 부트 지원 시작)
- 프레임워크를 쓰는 이유 : 개발 용이성, 시스템 복잡도 감소, 이식성, 품질보증, 운영 용이성, 개발코드 최소화, 변경 용이성, 설계와 코드의 재사용성

---

## 2. 개발 환경 설정

### VS Code 확장 모듈 (설치 후 재시작 필요)

| 확장 팩 | 자동 추가되는 확장 |
|---|---|
| **Extension Pack for Java** | Visual Studio IntelliCode, Language Support for Java, Debugger for Java, Maven for Java, Java Test Runner, Project Manager for Java |
| **Spring Boot Extension Pack** | Spring Boot Tools, Spring Initializr Java Support, Spring Boot Dashboard, Cloudfoundry Manifest YML, Concourse CI Pipeline Editor |

### JDK 환경 설정 (Java 연동에 문제가 생긴 경우)

`파일 → 기본설정 → 설정` 에서 `java home` 검색 후 `settings.json`에서 편집

```jsonc
// Windows
{
  "spring-boot.ls.java.home": "C:\\Program Files\\Java\\jdk-25.0.2",
  "java.jdt.ls.java.home":    "C:\\Program Files\\Java\\jdk-25.0.2"
}
```

```jsonc
// macOS (이 저장소의 실제 설정)
{
  "spring-boot.ls.java.home": "/opt/homebrew/opt/openjdk@25/libexec/openjdk.jdk/Contents/Home",
  "java.jdt.ls.java.home":    "/opt/homebrew/opt/openjdk@25/libexec/openjdk.jdk/Contents/Home"
}
```

> **주의** : 경로 2개를 모두 추가해야 하며, 버전 경로를 정확히 확인할 것

### 새 프로젝트 만들기 (Spring Initializr)

1. 팔레트 `Ctrl + Shift + P` → **spring maven** 입력 → `Spring Initializr: Create a Maven Project`
2. 스프링 부트 버전 : **4.x**
3. 프로젝트 그룹 : `com.example`
4. 패키징 : **Jar**
5. 언어 : 자바 / 자바 버전 : **25**
6. 의존성 **7가지** 선택
   - Spring Boot DevTools, Lombok, Spring Web, Spring Web Services, Thymeleaf, Spring Data JPA, MySQL Driver
7. 폴더 지정 → 생성 후 하단 알림에서 **Open**

> **주의** : 폴더 경로에 한글이 들어가면 안 됨

---

## 3. 프로젝트 폴더 구조 (Maven)

```
spring
├── .mvn                                # Maven Wrapper
├── .vscode                             # VS Code 설정 (launch.json, settings.json)
├── src
│   ├── main
│   │   ├── java/com/example/demo       # 루트 패키지 경로
│   │   │   ├── DemoApplication.java    # 프로그램 시작점
│   │   │   └── DemoController.java     # URL 매핑 담당
│   │   └── resources
│   │       ├── static                  # css, 이미지, js 등
│   │       ├── templates               # Thymeleaf 템플릿 (뷰)
│   │       └── application.properties  # 애플리케이션 환경 설정
│   └── test                            # 테스트 코드 (실행하면 생성됨)
├── target                              # 빌드 산출물
└── pom.xml                             # 빌드 및 종속성 설정
```

- **패키지** = 폴더 구조로 프로젝트를 구분하는 단위
- **Gradle vs Maven** : Maven은 기존 레거시에 적합하며 소규모 프로젝트에서 많이 사용

---

## 4. 자바 클래스 파일 — DemoApplication.java

```java
package com.example.demo; // 현재 폴더 위치

import org.springframework.boot.SpringApplication;              // 스프링 핵심 클래스
import org.springframework.boot.autoconfigure.SpringBootApplication; // 자동 설정 기능 활성화

@SpringBootApplication // 애노테이션(스프링 부트 APP 명시, 하위 다양한 설정을 자동 등록)
public class DemoApplication { // 클래스 이름

    public static void main(String[] args) { // 메인 메서드(프로그램 시작점)
        SpringApplication.run(DemoApplication.class, args); // run 메서드로 실행
    }
}
```

### 주요 스프링 부트 애노테이션

| 애노테이션 | 의미 |
|---|---|
| `@SpringBootApplication` | Spring Boot application 으로 설정 |
| `@Controller` | View를 제공하는 controller로 설정 |
| `@RestController` | REST API를 제공하는 controller로 설정 |
| `@RequestMapping` | URL 주소를 맵핑 |
| `@GetMapping` | Http GetMethod URL 주소 맵핑 |
| `@PostMapping` | Http PostMethod URL 주소 맵핑 |
| `@PutMapping` | Http PutMethod URL 주소 맵핑 |
| `@DeleteMapping` | Http DeleteMethod URL 주소 맵핑 |
| `@RequestParam` | URL Query Parameter 맵핑 |
| `@RequestBody` | Http Body를 Parsing 맵핑 |
| `@Valid` | POJO Java class의 검증 |

### JVM (자바 가상 머신)

바이트 코드를 실행/해석/JIT 컴파일하며, C#과 같이 **GC**(가비지 컬렉션)로 메모리를 자동 관리

| 특징 | 설명 | 장점 | 단점 |
|---|---|---|---|
| 플랫폼 독립성 | 운영체제나 하드웨어에 독립적으로 작동 | 어떤 환경에서도 동일하게 실행 | 초기 컴파일 과정 필요 |
| 바이트코드 실행 | 소스 코드를 바이트코드로 컴파일하여 실행 | 플랫폼 독립성 확보 | 인터프리터 또는 JIT 과정 필요 |
| 가비지 컬렉션 | 개발자가 메모리 관리를 직접 하지 않음 | 메모리 누수 방지 | 초기 성능 저하 가능성 |
| 보안 | 샌드박스 방식으로 실행 | 시스템 보안 강화 | 복잡한 구현 필요 |
| 성능 | JIT 컴파일러 사용 | 빠른 실행 속도 | 초기 컴파일 과정 필요 |

---

## 5. 기본 화면 실행하기

`src/main/resources/templates` 하위에 **index.html** 생성 (기존 HTML5를 그대로 사용 가능)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>index 메인페이지</title>
</head>
<body>
    <h1>안녕하세요!</h1>
    <a href="/hello">홈페이지 메인</a>
</body>
</html>
```

`DemoApplication.java`를 열고 **디버깅 없이 실행 (Ctrl + F5)**

```
Tomcat initialized with port 8080 (http)
Tomcat started on port 8080 (http) with context path ''
Started DemoApplication in 0.625 seconds
```

**실행 결과 — http://localhost:8080**

![index 메인페이지](docs/images/index.png)

### 실행 시 에러가 나는 경우

DB 관련 모듈을 아직 설정하지 않았으므로 `pom.xml` 최상단의 의존성을 **주석 처리** (주석 단축키 : 선택 후 `Ctrl + /`)

```xml
<!-- <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency> -->
```

> 이 저장소에서는 `spring-boot-starter-data-jpa`, `spring-boot-starter-data-jpa-test`, `mysql-connector-j` 를 주석 처리함

---

## 6. 스프링 부트 동작 과정

### 웹 서버와 WAS

- 대표 웹 서버 : NGINX, **APACHE**, IIS
- 정적 웹 페이지 → **동적 웹 페이지**로 변화하며 DB와 서버 사이드 언어가 확장됨
- 현존 대부분의 웹 서버는 기본 WAS를 기반으로 다수 서버(대표적으로 **DB 서버**)를 연동

```
Client ── Web Server ── WAS ── DB
```

- 스프링 부트는 **톰캣(Tomcat)을 내장** → 자바 기반 동적 웹 페이지 실행
- **스프링 컨테이너** : 자바 객체의 생성·초기화·호출·종료를 관리하고 **의존성 주입(DI)** 수행

### DispatcherServlet 내부 동작

**프론트 컨트롤러** 역할로 페이지 요청/응답을 처리

```
1. 사용자가 /이름 형태로 URI 요청
2. DispatcherServlet → HandlerMapping 에게 매핑되는 컨트롤러 검색 요청
3. Controller 가 비즈니스 로직 수행 후 ModelAndView 반환
4. ViewResolver 가 뷰를 결정
5. View(Thymeleaf)가 서버 측에서 HTML 렌더링 후 응답
```

### URI 와 URL

```
http://www.example.co.uk/seo-tools/uri-encoder?name=value
└── Domain ──┘└──────── URI ────────┘└─ Query String ─┘
```

| 용어 | 의미 |
|---|---|
| Domain | 웹사이트가 호스팅된 실제 서버 |
| URI | 서버의 파일에 매핑되는 식별자 (자원의 **이름**) |
| URL | 자원의 **위치** |
| Query String | GET 요청으로 값을 전달하는 부분 |

### MVC 디자인 패턴

| 구성 | 역할 |
|---|---|
| **M** (Model) | 페이지 정보 저장 |
| **V** (View) | 페이지 화면 구성 — 템플릿 엔진 : jsp, thymeleaf 등 |
| **C** (Controller) | 페이지 흐름 제어 |

> 디자인 패턴 = 개발에 앞서 설계 구조를 정형화한 방법론

---

## 7. Thymeleaf

HTML 기반 템플릿 엔진으로 MVC에서 **뷰(V)** 역할을 담당하며, `DispatcherServlet`을 통해 동작하고 `TemplateEngine`을 내장

| 템플릿 | Spring Boot starter dependency |
|---|---|
| FreeMarker | `spring-boot-starter-freemarker` |
| Groovy templates | `spring-boot-starter-groovy-templates` |
| JavaServer Pages (JSP) | None (provided by Tomcat or Jetty) |
| Mustache | `spring-boot-starter-mustache` |
| **Thymeleaf** | `spring-boot-starter-thymeleaf` |

> 레거시인 **JSP는 사용 금지**

### 문법

| 구분 | 문법 |
|---|---|
| 선언 | `<html lang="en" xmlns:th="http://www.thymeleaf.org">` |
| 주석 | `<!--/* This code will be removed at thymeleaf parsing time! */-->` |
| 텍스트 출력 | `<span th:text="${data}"> </span>` |
| 변수 표현식 | `${...}` |

### 동작 규칙

- 모든 링크는 **컨트롤러를 통해** 동작
- URL 요청 → 컨트롤러가 **viewName** 리턴 → `templates/viewName.html` 과 연결
- `resources/templates` 안에 뷰를, `resources/static` 에는 그 외 자원을 배치

---

## 8. URL 매핑과 컨트롤러

`src/main/java/com/example/demo` 하위에 **DemoController.java** 생성 (대소문자 및 이름 일치 주의)

```java
package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 컨트롤러 애노테이션 명시
public class DemoController {

    @GetMapping("/hello") // 전송 방식 GET
    public String hello(Model model) {
        model.addAttribute("data", "반갑습니다."); // model 설정
        return "hello"; // hello.html 연결
    }
}
```

### 클래스 이름 작성 규칙

| 작성 규칙 | 예 |
|---|---|
| 하나 이상의 문자로 이루어져야 한다 | `Car`, `SportsCar` |
| 첫 번째 글자는 숫자가 올 수 없다 | `3Car` (X) |
| `$`, `_` 외의 특수문자는 사용할 수 없다 | `$Car`, `_Car`, `@Car`(X), `#Car`(X) |
| 자바 키워드는 사용할 수 없다 | `int`(X), `for`(X) |

### 주의 — 404 에러

`index.html`에서 링크를 `/hello.html` 로 걸면 **Whitelabel Error Page (404)** 가 발생

```
No static resource hello.html.
NoResourceFoundException
```

정적 파일이 아니라 **컨트롤러 매핑 주소**를 써야 하므로 `/hello` 로 수정한다.

**실행 결과 — http://localhost:8080/hello**

![hello 페이지](docs/images/hello.png)

### 수업 완료 시점의 URL 매핑

| URL | 컨트롤러 메서드 | 연결 화면 | 출력 |
|---|---|---|---|
| `/` | (정적 연결) | `index.html` | 안녕하세요! |
| `/hello` | `hello()` | `hello.html` | 안녕하세요! / 반갑습니다. |

---

# 2주차 과제

## 문제 — URL 매핑과 컨트롤러 추가하기

1. 기존 `hello.html` 링크를 수정한다
   - 링크명 : **두번째 헬로 페이지**
   - URL 매핑으로 통일 : **`/hello2`**
2. 컨트롤러에 매핑을 추가한다
   - `hello2` 메서드를 작성하고 **5개의 속성**을 각자 추가
3. **`hello2.html`** 을 작성한다
   - 5개 속성 변수를 출력

## 구현

### 1) hello.html — 링크 수정

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Hello 페이지</title>
</head>
<body>
    <h1>안녕하세요!</h1>
    <p th:text="${data}"></p>
    <a href="/hello2">두번째 헬로 페이지</a>
</body>
</html>
```

![hello 페이지](docs/images/hello.png)

### 2) DemoController.java — hello2 매핑 추가

```java
@GetMapping("/hello2") // 2주차 연습문제 : URL 매핑 추가
public String hello2(Model model) {
    model.addAttribute("name", "홍길동님.");        // 속성 1
    model.addAttribute("greeting", "방갑습니다.");   // 속성 2
    model.addAttribute("day", "오늘.");             // 속성 3
    model.addAttribute("weather", "날씨는.");        // 속성 4
    model.addAttribute("comment", "매우 좋습니다.");  // 속성 5
    return "hello2"; // hello2.html 연결
}
```

`model.addAttribute("키", "값")` 으로 담은 값을 뷰에서 `${키}` 로 꺼내 쓴다.

### 3) hello2.html — 5개 속성 출력

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Hello2 페이지</title>
</head>
<body>
    <h1>안녕하세요!</h1>
    <p th:text="${name}"></p>
    <p th:text="${greeting}"></p>
    <p th:text="${day}"></p>
    <p th:text="${weather}"></p>
    <p th:text="${comment}"></p>
    <a href="/">홈페이지 메인</a>
</body>
</html>
```

**실행 결과 — http://localhost:8080/hello2**

![hello2 페이지](docs/images/hello2.png)

## 최종 URL 매핑 정리

| URL | 컨트롤러 메서드 | 뷰 | 전달 속성 | 다음 링크 |
|---|---|---|---|---|
| `/` | (정적 연결) | `index.html` | - | `/hello` |
| `/hello` | `hello()` | `hello.html` | `data` | `/hello2` |
| `/hello2` | `hello2()` | `hello2.html` | `name`, `greeting`, `day`, `weather`, `comment` | `/` |

화면 이동 흐름 : `/` → `/hello` → `/hello2` → `/` (순환)

---

## 시험 대비 핵심 정리

| 질문 | 답 |
|---|---|
| 스프링 부트의 핵심 특징은? | 자동 환경설정, 자동 빌드 및 리로드, WAS 내장 |
| 스프링 부트에 내장된 WAS는? | Apache Tomcat (기본 8080 포트) |
| 프론트 컨트롤러 역할을 하는 것은? | DispatcherServlet |
| MVC 각각의 역할은? | Model=정보 저장, View=화면 구성, Controller=흐름 제어 |
| 컨트롤러가 리턴한 문자열의 의미는? | viewName — `templates/viewName.html` 과 연결 |
| 뷰에 값을 전달하는 방법은? | `model.addAttribute("키", "값")` → `th:text="${키}"` |
| Thymeleaf 선언 방법은? | `<html xmlns:th="http://www.thymeleaf.org">` |
| 뷰 파일과 정적 자원의 위치는? | `resources/templates` / `resources/static` |
| 뷰가 404가 나는 대표적 원인은? | 링크를 `/hello.html`처럼 파일명으로 걸었을 때 (→ `/hello` 로 수정) |
| GET 요청을 매핑하는 애노테이션은? | `@GetMapping` |
| 스프링 부트 앱임을 명시하는 애노테이션은? | `@SpringBootApplication` |
| JVM이 메모리를 자동 관리하는 기능은? | GC (가비지 컬렉션) |
