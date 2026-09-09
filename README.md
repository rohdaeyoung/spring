# SPRING

> # 스프링 부트 프로젝트 시작! (학번 :20230987 이름 : 노대영 )
>
> 매 주 수업 내용을 정리하자.

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

## 1. 스프링 부트란

### 스프링 프레임워크에서 스프링 부트로

2004년에 나온 스프링 프레임워크는 오픈소스 기반의 대표 웹 프레임워크지만, 모든 설정을 XML로 직접 작성해야 해서 **진입 장벽이 매우 높았다.** 이 문제를 해결하려고 2014년에 등장한 것이 스프링 부트다.

| 구분 | 스프링 프레임워크 (2004) | 스프링 부트 (2014) |
|---|---|---|
| 설정 | XML 기반 수동 설정 | **자동 환경설정** |
| 진입 장벽 | 매우 높음 | 낮음 |
| 서버 | 외부 WAS 별도 설치 | **WAS 내장** |
| 빌드 | 수동 | 자동 빌드 및 리로드 |

스프링 부트는 스프링을 **대체한 것이 아니라 감싼 것**이다. 아래에 스프링이 그대로 있고, 그 위에서 설정을 자동화해 준다.

> **비유** — 스프링이 재료를 하나하나 직접 손질하는 것이라면, 스프링 부트는 재료를 믹서기에 넣고 버튼만 누르는 것이다.

**핵심 정리**
- 스프링 부트의 핵심은 **작은 규모 및 단순 서블릿 관리**
- 자바 언어로 작성되어 **JVM 위에서 실행**된다
- 주요 기능은 **자동 환경설정**, 자동 빌드 및 리로드

### 전자정부 표준 프레임워크 (eGov)

스프링을 기반으로 2010년 이후 공개된, 국내 공공기관 웹 프로젝트의 표준이다. 요구 기술은 **자바, JSP, jQuery, eGov**이며 최근 스프링 부트 지원이 시작되었다.

프레임워크를 쓰는 이유는 다음 8가지로 정리된다.

| 개발 용이성 | 시스템 복잡도 감소 | 이식성 | 품질보증 |
|---|---|---|---|
| **운영 용이성** | **개발코드의 최소화** | **변경 용이성** | **설계와 코드의 재사용성** |

---

## 2. 개발 환경 설정

### 2-1. VS Code 확장 모듈

직접 검색해서 설치한 뒤 **VS Code를 재시작**해야 적용된다.

| 설치할 확장 팩 | 자동으로 함께 추가되는 확장 |
|---|---|
| **Extension Pack for Java** | Visual Studio IntelliCode, Language Support for Java, Debugger for Java, Maven for Java, Java Test Runner, Project Manager for Java |
| **Spring Boot Extension Pack** | Spring Boot Tools, Spring Initializr Java Support, Spring Boot Dashboard, Cloudfoundry Manifest YML, Concourse CI Pipeline Editor |

### 2-2. JDK 환경 설정

자바 연동에 문제가 생긴 경우에만 하면 된다.

`파일 → 기본설정 → 설정` → 검색창에 **java home** 입력 → **`settings.json`에서 편집** 클릭 → 경로 **2개**를 직접 추가한다.

```jsonc
// Windows — 백슬래시를 이스케이프해야 함
{
  "spring-boot.ls.java.home": "C:\\Program Files\\Java\\jdk-25.0.2",
  "java.jdt.ls.java.home":    "C:\\Program Files\\Java\\jdk-25.0.2"
}
```

```jsonc
// macOS — 이 저장소의 실제 설정
{
  "spring-boot.ls.java.home": "/opt/homebrew/opt/openjdk@25/libexec/openjdk.jdk/Contents/Home",
  "java.jdt.ls.java.home":    "/opt/homebrew/opt/openjdk@25/libexec/openjdk.jdk/Contents/Home"
}
```

> **주의** — 설치된 JDK의 **버전 경로를 정확히** 확인할 것

### 2-3. 새 프로젝트 만들기 (Spring Initializr)

팔레트 `Ctrl + Shift + P` → **spring maven** 입력 → `Spring Initializr: Create a Maven Project`

| 단계 | 선택 |
|---|---|
| 1 | 스프링 부트 버전 : **4.x** |
| 2 | 프로젝트 그룹 : `com.example` |
| 3 | 패키징 : **Jar** |
| 4 | 언어 : 자바 / 자바 버전 : **25** |
| 5 | 의존성 **7가지** 선택 |
| 6 | 새 폴더를 생성한 뒤 지정 |
| 7 | 하단 알림에서 **Open** → 폴더 신뢰 OK |

선택할 의존성 7가지

| 분류 | 의존성 |
|---|---|
| Developer Tools | Spring Boot DevTools, Lombok |
| Web | Spring Web, Spring Web Services |
| Template Engines | Thymeleaf |
| SQL | Spring Data JPA, MySQL Driver |

> **주의** — 폴더 경로에 **한글이 들어가면 안 된다.**

프로젝트가 열리면 하단에 뜨는 설정 알림을 **모두 수락**한다.

---

## 3. 프로젝트 폴더 구조 (Maven)

```
spring
├── .mvn                                # Maven Wrapper
├── .vscode                             # VS Code 설정
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

기억할 점
- **`src → main → java`** 가 루트 패키지 경로다
- **패키지 = 폴더 구조**로 프로젝트를 구분하는 단위
- 빌드 도구는 **Gradle**과 **Maven**이 있으며, Maven은 기존 레거시에 적합하고 소규모 프로젝트에서 많이 쓰인다

---

## 4. 자바 클래스 파일 — DemoApplication.java

`demo` 폴더 하위의 `.java` 소스파일이며, **프로그램의 시작점**이다.

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

스프링 부트는 **애노테이션을 통해 자동 설정**된다. 코드 상단에 특정 역할을 명시하는 방식이다.

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

자바 소스는 **바이트 코드**로 컴파일되고, JVM이 이를 실행/해석/JIT 컴파일한다. C#과 같이 **GC**(가비지 컬렉션)가 있어 메모리를 자동 관리한다.

```
[컴파일 타임]  Hello.java  ──javac──▶  Hello.class (바이트 코드)
[런타임]       Hello.class ──클래스 로더──▶ JVM ──▶ 운영체제 ──▶ 하드웨어
```

| 특징 | 설명 | 장점 | 단점 |
|---|---|---|---|
| 플랫폼 독립성 | 운영체제나 하드웨어에 독립적으로 작동 | 어떤 환경에서도 동일하게 실행 | 초기 컴파일 과정 필요 |
| 바이트코드 실행 | 소스 코드를 바이트코드로 컴파일하여 실행 | 플랫폼 독립성 확보 | 인터프리터 또는 JIT 과정 필요 |
| 가비지 컬렉션 | 개발자가 메모리 관리를 직접 하지 않음 | 메모리 누수 방지 | 초기 성능 저하 가능성 |
| 보안 | 샌드박스 방식으로 실행 | 시스템 보안 강화 | 복잡한 구현 필요 |
| 성능 | JIT 컴파일러 사용 | 빠른 실행 속도 | 초기 컴파일 과정 필요 |

---

## 5. 기본 화면 실행하기

`src/main/resources/templates` 하위에 **index.html**을 생성한다. 기존 HTML5를 그대로 사용할 수 있다.

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

`DemoApplication.java`를 열고 메인 함수를 확인한 뒤 **디버깅 없이 실행 (`Ctrl + F5`)** 한다. 초기 로딩에 시간이 걸리므로 잠시 대기한다.

```
Tomcat initialized with port 8080 (http)
Starting Servlet engine: [Apache Tomcat/10.1.26]
Tomcat started on port 8080 (http) with context path ''
Started DemoApplication in 0.625 seconds
```

좌측 **Spring Boot Dashboard**에서 `demo [:8080]` 항목의 인터넷 아이콘을 클릭하거나, 브라우저에서 직접 접속한다.

**실행 결과 — http://localhost:8080**

![index 메인페이지](docs/images/index.png)

### 실행 시 에러가 나는 경우

DB 관련 소프트웨어를 아직 설정하지 않았으므로, 최상단 `pom.xml`에서 DB 관련 의존성을 **주석 처리**한다. 주석 단축키는 선택 후 `Ctrl + /` 이다.

```xml
<!-- <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency> -->
```

> 이 저장소에서는 `spring-boot-starter-data-jpa`, `spring-boot-starter-data-jpa-test`, `mysql-connector-j` 를 주석 처리했다.

**왜 주석 처리해야 하나** — 스프링 부트는 시작할 때 JPA와 MySQL Driver를 발견하면 DB 접속 정보(DataSource)를 자동으로 만들려 한다. 그런데 아직 접속 정보를 설정하지 않았으므로 실패하면서 애플리케이션이 종료된다.

---

## 6. 스프링 부트 동작 과정

### 6-1. 웹 서버와 WAS

대표적인 웹 서버로 NGINX, **APACHE**, IIS 등이 있다. 웹은 **정적 웹 페이지에서 동적 웹 페이지 개발로 변화**했고, 이에 따라 DB와 서버 사이드 언어가 확장되었다.

현존하는 대부분의 웹 서버는 기본 WAS를 기반으로 다수의 서버를 연동하며, 대표적인 것이 **DB 서버**다.

```
Client ── Web Server ── WAS ── DB
```

스프링 부트 프레임워크는 **톰캣(TOMCAT)을 내장**하고 있다.
- 웹 애플리케이션 서버(WAS)
- 자바 기반 동적 웹 페이지 실행
- 오픈소스

또한 **스프링 컨테이너**가 자바 객체의 **생성, 초기화, 호출, 종료**를 관리하고 **의존성 주입(DI)** 을 수행한다.

### 6-2. DispatcherServlet 내부 동작

`DispatcherServlet`은 **프론트 컨트롤러** 역할로 페이지 요청과 응답을 처리한다.

```
1. 사용자가 /이름 형태로 URI 요청
2. DispatcherServlet → HandlerMapping 에게 매핑되는 컨트롤러 검색 요청
3. Controller 가 비즈니스 로직 수행 후 ModelAndView 반환
4. ViewResolver 가 처리 결과를 생성할 뷰를 결정
5. View(Thymeleaf)가 서버 측에서 HTML 렌더링 후 클라이언트에 응답
```

`/hello` 를 입력했을 때 실제로 일어나는 일

```
브라우저 → 내장 톰캣 → DispatcherServlet
  → HandlerMapping이 DemoController.hello() 를 찾음
  → Model에 "data" = "반갑습니다." 를 담고 "hello" 리턴
  → ViewResolver가 templates/hello.html 을 찾음
  → Thymeleaf가 ${data}를 "반갑습니다."로 치환해 HTML 완성
  → 브라우저에 응답
```

### 6-3. URI 와 URL

```
http://www.example.co.uk/seo-tools/uri-encoder?name=value
└── Domain ──┘└──────── URI ────────┘└─ Query String ─┘
```

| 용어 | 의미 |
|---|---|
| Domain | 웹사이트가 호스팅된 실제 서버 |
| URI | 서버의 파일에 매핑되는 식별자 — 자원의 **이름** |
| URL | 자원의 **위치** |
| Query String | GET 요청으로 값을 전달하는 부분 |

### 6-4. MVC 디자인 패턴

디자인 패턴이란 개발에 앞서 **설계 구조를 정형화한 방법론**이다.

| 구성 | 역할 | 비고 |
|---|---|---|
| **M** (Model) | 페이지 정보 저장 | 컨트롤러가 뷰로 넘길 데이터를 담음 |
| **V** (View) | 페이지 화면 구성 | 템플릿 엔진 : jsp, thymeleaf 등 |
| **C** (Controller) | 페이지 흐름 제어 | URL을 받아 어떤 뷰를 보여줄지 결정 |

```
                 Web request
                      ↓
                 Controller
            ↙                  ↘
   Update data              Update presentation
      Model    ◀── Get data ──   View
```

---

## 7. Thymeleaf

HTML 기반 **템플릿 엔진**으로, MVC에서 화면 출력을 담당하는 **뷰(V)** 역할을 한다. `DispatcherServlet`을 통해 동작하며 `TemplateEngine`을 내장하고 있다. HTML, XML, JS, CSS 등을 지원한다.

주요 기능은 데이터 바인딩·연산·객체 호출, 템플릿 Fragment, 전용 제어문(반복·조건) 등이다.

| 템플릿 | Spring Boot starter dependency |
|---|---|
| FreeMarker | `spring-boot-starter-freemarker` |
| Groovy templates | `spring-boot-starter-groovy-templates` |
| JavaServer Pages (JSP) | None (provided by Tomcat or Jetty) |
| Mustache | `spring-boot-starter-mustache` |
| **Thymeleaf** | `spring-boot-starter-thymeleaf` |

> 레거시 템플릿 엔진인 **JSP는 사용 금지**다.

### 문법

| 구분 | 문법 |
|---|---|
| 선언 | `<html lang="en" xmlns:th="http://www.thymeleaf.org">` |
| 주석 | `<!--/* This code will be removed at thymeleaf parsing time! */-->` |
| 텍스트 출력 | `<span th:text="${data}"> </span>` |
| 변수 표현식 | `${...}` |

### 동작 규칙

- **모든 링크는 컨트롤러를 통해 동작**한다
- URL 요청 이후 컨트롤러는 **viewName**을 리턴하고, `templates/viewName.html` 과 연결된다
- `resources/templates` 에는 뷰를, `resources/static` 에는 그 외 자원을 배치한다

---

## 8. URL 매핑과 컨트롤러

루트 폴더 하위 `demo` 폴더에 **DemoController.java** 를 생성한다. **대소문자 및 이름이 일치해야 한다.**

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

읽는 법
- `@GetMapping("/hello")` — 주소창에 `/hello` 를 치면 이 메서드가 실행된다
- `model.addAttribute("data", "반갑습니다.")` — 뷰로 넘길 데이터를 `data` 라는 이름으로 담는다
- `return "hello"` — 이 문자열이 **viewName**이며 `templates/hello.html` 과 연결된다

### 클래스 이름 작성 규칙

| 작성 규칙 | 예 |
|---|---|
| 하나 이상의 문자로 이루어져야 한다 | `Car`, `SportsCar` |
| 첫 번째 글자는 숫자가 올 수 없다 | `3Car` (X) |
| `$`, `_` 외의 특수문자는 사용할 수 없다 | `$Car`, `_Car` / `@Car`(X), `#Car`(X) |
| 자바 키워드는 사용할 수 없다 | `int`(X), `for`(X) |

### 404 에러가 나는 경우

`index.html`에서 링크를 `/hello.html` 로 걸면 **Whitelabel Error Page (404)** 가 발생한다.

```
This application has no explicit mapping for /error, so you are seeing this as a fallback.
There was an unexpected error (type=Not Found, status=404).
No static resource hello.html.
NoResourceFoundException
```

`templates` 폴더의 파일은 정적 자원이 아니라 **컨트롤러를 거쳐야만** 접근된다. 따라서 링크를 파일명이 아닌 **매핑 주소 `/hello`** 로 수정해야 한다.

```html
<a href="/hello.html">홈페이지 메인</a>   <!-- ✗ 404 -->
<a href="/hello">홈페이지 메인</a>        <!-- ✓ -->
```

**실행 결과 — http://localhost:8080/hello**

![hello 페이지](docs/images/hello.png)

`th:text="${data}"` 가 컨트롤러에서 담은 `"반갑습니다."` 로 치환되어 출력된 것이다.

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

## 풀이 순서

### 1단계. hello.html 의 링크를 수정한다

기존 링크를 `/hello2` 로 바꾸고 링크명을 "두번째 헬로 페이지"로 수정한다.

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

여기서도 `/hello2.html` 이 아니라 **`/hello2`** 로 써야 한다. 파일명으로 쓰면 404가 난다.

**실행 결과 — http://localhost:8080/hello**

![hello 페이지](docs/images/hello.png)

### 2단계. 컨트롤러에 hello2 매핑을 추가한다

`DemoController.java` 에 메서드를 하나 더 만든다. 속성 5개를 각각 다른 이름으로 담는다.

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

`addAttribute("키", "값")` 으로 담은 값을 뷰에서 `${키}` 로 꺼내 쓴다. **키 이름이 서로 달라야** 5개가 모두 출력된다.

### 3단계. hello2.html 을 작성한다

담은 속성 5개를 `th:text` 로 각각 출력한다.

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

## 결과 정리

| URL | 컨트롤러 메서드 | 뷰 | 전달 속성 | 다음 링크 |
|---|---|---|---|---|
| `/` | (정적 연결) | `index.html` | - | `/hello` |
| `/hello` | `hello()` | `hello.html` | `data` | `/hello2` |
| `/hello2` | `hello2()` | `hello2.html` | `name`, `greeting`, `day`, `weather`, `comment` | `/` |

화면 이동 흐름

```
/  ──▶  /hello  ──▶  /hello2  ──▶  /
```

이 과제로 확인한 것은 **컨트롤러에 매핑을 추가하는 방법**, **Model로 값을 여러 개 전달하는 방법**, 그리고 **Thymeleaf에서 변수를 출력하는 방법** 세 가지다.
