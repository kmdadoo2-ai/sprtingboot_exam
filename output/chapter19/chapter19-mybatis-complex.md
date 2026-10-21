# 19장 MyBatis로 JOIN 조회 결과 출력하기

18장에서는 MyBatis에서 SQL 실행 결과를 숫자로 전달받는 방법을 살펴보았습니다. 이번 장에서는 두 테이블을 연결하여 조회한 결과를 하나의 DTO에 담아 화면에 출력합니다.

직원 테이블에는 직원 이름과 부서 번호가 있고, 부서 테이블에는 부서 번호와 부서 이름이 있습니다. 직원 이름 옆에 부서 이름까지 표시하려면 두 테이블을 함께 조회해야 합니다. 이때 사용하는 SQL이 JOIN입니다.

이번 실습은 `EMP`와 `DEPT`를 조인하여 **10번 부서에 소속된 직원의 이름, 부서 번호, 부서 이름**을 출력합니다. 프로젝트 이름과 주요 클래스 이름은 기존 실습 자료의 `Ex19_MyBatis_Complex`, `UserDTO`, `IDao`를 유지합니다.

## 19.1 학습 목표

- 두 테이블을 JOIN하여 필요한 열을 조회합니다.
- SQL의 별칭과 DTO 프로퍼티의 관계를 이해합니다.
- 조회 결과 여러 행을 `List<UserDTO>`로 전달받습니다.
- Controller에서 Model에 담은 데이터를 JSP에서 반복 출력합니다.
- `resultType`과 `resultMap`의 역할을 구분합니다.

이번 장의 핵심 흐름은 다음과 같습니다.

```text
브라우저 GET /employee
    → MyController
    → IDao.getEmployee()
    → UserDao.xml의 JOIN SQL
    → Oracle의 EMP, DEPT 조회
    → List<UserDTO>
    → Model의 employees
    → employeelist.jsp
```

## 19.2 프로젝트 생성하기

Spring Initializr 또는 Spring Tools의 New Spring Starter Project에서 다음과 같이 생성합니다.

| 항목 | 설정 |
|---|---|
| Project / Type | Gradle - Groovy |
| Language | Java |
| Spring Boot | 4.1.1 |
| Java | 21 |
| Name / Artifact | Ex19_MyBatis_Complex |
| Group | com.study |
| Package name | com.study.springboot |
| Packaging | War |
| Dependencies | JDBC API, Lombok, MyBatis Framework, Oracle Driver, Spring Web |

앞선 JSP 실습과 같이 WAR 프로젝트를 사용합니다. Spring Boot에서 내장 Tomcat과 JSP를 사용할 때 실행 가능한 WAR로 구성할 수 있으며, 실행 가능한 JAR는 JSP를 지원하지 않습니다. [Spring Boot의 JSP 제약 사항](https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.embedded-container.jsp-limitations)

이 장에서는 Lombok을 의존성으로 추가합니다. 별도의 Gradle Lombok 플러그인은 적용하지 않습니다. Eclipse 기반 Spring Tools에서는 앞 장에서 설명한 IDE용 Lombok 설치도 완료되어 있어야 합니다.

### Gradle 설정

파일: `Ex19_MyBatis_Complex/build.gradle`

```groovy
plugins {
    id 'java'
    id 'war'
    id 'org.springframework.boot' version '4.1.1'
    id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.study'
version = '0.0.1-SNAPSHOT'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'org.springframework.boot:spring-boot-starter-webmvc'
    implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:4.0.1'

    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
    runtimeOnly 'com.oracle.database.jdbc:ojdbc17'

    providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat-runtime'
    providedRuntime 'org.apache.tomcat.embed:tomcat-embed-jasper'
    implementation 'jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api'
    implementation 'org.glassfish.web:jakarta.servlet.jsp.jstl'

    testImplementation 'org.springframework.boot:spring-boot-starter-webmvc-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.withType(JavaCompile).configureEach {
    options.encoding = 'UTF-8'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

MyBatis Spring Boot Starter 4.0 계열은 Spring Boot 4.0 이상과 Java 17 이상을 지원하므로 Java 21 환경에 해당합니다. 이 장은 기존 18장 프로젝트와 같은 Starter 4.0.1을 사용합니다. **Starter의 버전과 MyBatis 본체의 버전은 별개**입니다. [MyBatis Starter의 지원 환경](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

Tomcat과 Jasper는 WAR의 `providedRuntime`으로 지정합니다. 실행 가능한 WAR에서는 내장 실행에 필요한 라이브러리를 포함하면서 외부 컨테이너 배포도 고려할 수 있습니다. 오래된 `javax.servlet:jstl`이나 `org.glassfish:jakarta.el:4.0.2`를 추가하지 않고 위 설정을 사용합니다.

Wrapper는 프로젝트 생성 시 함께 받은 `gradlew`, `gradlew.bat`, `gradle/wrapper` 파일을 사용합니다. 기존 실습과 같은 Gradle 9.7.1을 사용할 경우 `gradle/wrapper/gradle-wrapper.properties`의 배포 주소는 다음과 같습니다. 다른 설정은 생성된 값을 유지합니다.

```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-9.7.1-bin.zip
```

## 19.3 데이터베이스 준비하기

이번 실습에 필요한 열은 다음과 같습니다.

| 테이블 | 열 | 의미 |
|---|---|---|
| EMP | EMPNO | 직원 번호 |
| EMP | ENAME | 직원 이름 |
| EMP | DEPTNO | 소속 부서 번호 |
| DEPT | DEPTNO | 부서 번호 |
| DEPT | DNAME | 부서 이름 |

`EMP.DEPTNO`와 `DEPT.DEPTNO`를 연결하면 직원의 부서 이름을 찾을 수 있습니다.

원본 자료는 `hr` 계정으로 `EMP`, `DEPT`를 조회하지만, **Oracle의 일반적인 HR 샘플 스키마는 `EMPLOYEES`, `DEPARTMENTS`를 사용합니다.** 계정 이름이 `hr`이라는 이유만으로 `EMP`, `DEPT`가 있다고 가정하면 안 됩니다. 앞선 실습에서 해당 테이블을 만든 계정으로 접속하거나, 아래 최소 실습 데이터를 별도의 실습 스키마에 준비합니다.

### 실습 테이블이 없는 경우

아래 SQL은 `EMP`, `DEPT`가 없는 실습용 스키마에서 한 번만 실행합니다. 이미 같은 이름의 테이블이 있으면 삭제하거나 다시 생성하지 말고 필요한 열과 데이터를 확인하세요. 아래 SQL은 자동 실행용 `schema.sql`이 아니라 SQL Developer 등에서 직접 실행하는 준비용 SQL입니다.

```sql
CREATE TABLE dept (
    deptno NUMBER(2) PRIMARY KEY,
    dname VARCHAR2(30) NOT NULL
);

CREATE TABLE emp (
    empno NUMBER(4) PRIMARY KEY,
    ename VARCHAR2(30) NOT NULL,
    deptno NUMBER(2),
    CONSTRAINT fk_ch19_emp_dept
        FOREIGN KEY (deptno) REFERENCES dept(deptno)
);

INSERT INTO dept (deptno, dname) VALUES (10, 'ACCOUNTING');
INSERT INTO dept (deptno, dname) VALUES (20, 'RESEARCH');

INSERT INTO emp (empno, ename, deptno) VALUES (7782, 'CLARK', 10);
INSERT INTO emp (empno, ename, deptno) VALUES (7839, 'KING', 10);
INSERT INTO emp (empno, ename, deptno) VALUES (7934, 'MILLER', 10);
INSERT INTO emp (empno, ename, deptno) VALUES (7369, 'SMITH', 20);

COMMIT;
```

먼저 SQL 도구에서 다음 조회가 되는지 확인합니다.

```sql
SELECT e.ename, d.deptno, d.dname
FROM emp e
JOIN dept d ON e.deptno = d.deptno
WHERE d.deptno = 10
ORDER BY e.empno;
```

`JOIN ... ON`은 두 테이블을 연결하는 조건을, `WHERE`는 조회할 부서를 지정합니다. 이 쿼리는 INNER JOIN이므로 연결되는 부서가 없는 직원은 결과에 포함되지 않습니다. `ORDER BY`는 결과의 표시 순서를 일정하게 합니다.

## 19.4 애플리케이션 설정과 파일 구성

파일: `src/main/resources/application.properties`

```properties
spring.application.name=Ex19_MyBatis_Complex
server.port=8081

# JSP
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# Oracle
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# MyBatis
mybatis.mapper-locations=classpath:/mybatis/mapper/**/*.xml
```

`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`에는 앞에서 테이블을 준비한 데이터베이스 접속 정보를 지정합니다. Spring Tools에서는 실행 설정의 Environment 항목에 등록할 수 있습니다. 터미널에서는 19.9절의 실행 예시를 참고하세요.

`**/*.xml`은 지정한 매퍼 폴더와 그 하위 폴더의 XML을 찾는 패턴입니다. 여기에는 `UserDao.xml`이 들어갑니다.

프로젝트에서 직접 작성하거나 확인할 파일은 다음과 같습니다.

```text
Ex19_MyBatis_Complex/
├─ build.gradle
├─ src/main/java/com/study/springboot/
│  ├─ Ex19MyBatisComplexApplication.java
│  ├─ ServletInitializer.java
│  ├─ MyController.java
│  └─ jdbc/
│     ├─ UserDTO.java
│     └─ IDao.java
├─ src/main/resources/
│  ├─ application.properties
│  └─ mybatis/mapper/UserDao.xml
└─ src/main/webapp/WEB-INF/views/
   └─ employeelist.jsp
```

생성된 애플리케이션 클래스 이름이 다르면 아래 이름으로 통일하거나 `ServletInitializer`의 참조도 함께 변경합니다. 같은 역할의 시작 클래스를 중복해서 만들지 않습니다.

파일: `src/main/java/com/study/springboot/Ex19MyBatisComplexApplication.java`

```java
package com.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex19MyBatisComplexApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ex19MyBatisComplexApplication.class, args);
    }
}
```

파일: `src/main/java/com/study/springboot/ServletInitializer.java`

```java
package com.study.springboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ex19MyBatisComplexApplication.class);
    }
}
```

`ServletInitializer`는 외부 서블릿 컨테이너에 WAR를 배포할 때 진입점을 연결합니다. 이번 실습의 기본 실행 방법은 Gradle의 `bootRun`입니다.

## 19.5 조회 결과를 담을 DTO와 매퍼 만들기

### UserDTO 작성

파일: `src/main/java/com/study/springboot/jdbc/UserDTO.java`

```java
package com.study.springboot.jdbc;

import lombok.Data;

@Data
public class UserDTO {
    private String ename1;
    private int dno1;
    private String dname1;
}
```

이 DTO는 특정 테이블 하나를 그대로 옮긴 클래스가 아닙니다. 직원 이름은 `EMP`에서, 부서 번호와 부서 이름은 `DEPT`에서 가져와 **화면에 필요한 조회 결과 한 행**을 표현합니다.

`@Data`는 getter와 setter 등을 생성합니다. MyBatis가 값을 설정할 때 setter를 사용할 수 있고, JSP에서 `${dto.ename1}`을 읽으면 getter를 통해 값에 접근합니다.

`ename1`, `dno1`, `dname1`이라는 이름은 SQL의 별칭과 DTO의 연결을 보여 주기 위해 원본과 동일하게 사용합니다.

### IDao 작성

파일: `src/main/java/com/study/springboot/jdbc/IDao.java`

```java
package com.study.springboot.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDao {
    List<UserDTO> getEmployee();
}
```

`@Mapper`는 MyBatis 매퍼 인터페이스임을 나타냅니다. MyBatis가 인터페이스를 구현하는 프록시를 만들어 Spring에 등록하므로 직접 구현 클래스를 작성하지 않습니다.

여러 직원이 조회될 수 있어 반환형은 `List<UserDTO>`입니다. 원본의 `ArrayList` 대신 인터페이스인 `List`를 사용하여 특정 컬렉션 구현에 의존하지 않도록 합니다. 조회된 각 행이 하나의 `UserDTO`가 됩니다.

## 19.6 JOIN SQL과 DTO 연결하기

파일: `src/main/resources/mybatis/mapper/UserDao.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.study.springboot.jdbc.IDao">

    <select id="getEmployee"
            resultType="com.study.springboot.jdbc.UserDTO">
        SELECT e.ename AS ename1,
               d.deptno AS dno1,
               d.dname AS dname1
        FROM emp e
        JOIN dept d ON e.deptno = d.deptno
        WHERE d.deptno = 10
        ORDER BY e.empno
    </select>

</mapper>
```

### 매퍼 인터페이스와 XML의 연결

| XML 설정 | 연결 대상 |
|---|---|
| `namespace="com.study.springboot.jdbc.IDao"` | 매퍼 인터페이스의 전체 이름 |
| `id="getEmployee"` | 인터페이스의 메서드 이름 |
| `resultType="com.study.springboot.jdbc.UserDTO"` | 조회 결과 한 행을 담을 클래스 |

`getEmployee()`는 목록을 반환하지만 `resultType`에는 `List`가 아닌 **목록의 원소 타입**을 작성합니다.

### SQL 별칭이 필요한 이유

| SQL의 열 | 조회 결과 별칭 | DTO 프로퍼티 |
|---|---|---|
| `e.ename` | `ename1` | `ename1` |
| `d.deptno` | `dno1` | `dno1` |
| `d.dname` | `dname1` | `dname1` |

별칭 `AS ename1`은 데이터베이스의 실제 열 이름을 변경하는 것이 아닙니다. 이번 조회 결과에 사용할 열 이름을 지정하는 것입니다. MyBatis는 이 결과 이름과 DTO 프로퍼티를 대응시킵니다.

별칭 없이 `ename`, `deptno`, `dname`을 조회하면 현재 DTO의 `ename1`, `dno1`, `dname1`과 일치하지 않습니다. 자동 매핑이 되지 않아 값이 비어 보일 수 있습니다. `map-underscore-to-camel-case` 옵션도 `deptno`를 임의의 이름인 `dno1`로 바꾸어 주지는 않습니다.

여러 테이블에서 같은 이름의 열을 함께 조회할 때도 구분되는 별칭을 부여하면 매핑이 명확해집니다. 필요한 열만 명시하는 방식은 JOIN에서 `SELECT *`를 사용하는 것보다 결과 구조를 이해하기 쉽습니다.

Oracle에 전달되는 위 SELECT 문은 XML 안에서 끝에 세미콜론을 붙이지 않습니다. SQL Developer에서 사용하는 문장 구분자와 JDBC에 전달할 SQL 문자열을 구분해야 합니다.

## 19.7 Controller 작성하기

파일: `src/main/java/com/study/springboot/MyController.java`

```java
package com.study.springboot;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.IDao;
import com.study.springboot.jdbc.UserDTO;

@Controller
public class MyController {
    private final IDao userDao;

    public MyController(IDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/")
    @ResponseBody
    public String root() {
        return "MyBatis : 복잡한 JOIN 쿼리 결과 출력하기";
    }

    @GetMapping("/employee")
    public String employeeList(Model model) {
        List<UserDTO> employees = userDao.getEmployee();
        model.addAttribute("employees", employees);
        return "employeelist";
    }
}
```

생성자에서 `IDao`를 전달받습니다. 생성자가 하나이므로 `@Autowired`는 생략할 수 있습니다. `final` 필드는 Controller가 만들어질 때 필요한 매퍼가 정해진다는 점을 보여 줍니다.

`/employee` 요청이 들어오면 `getEmployee()`를 호출하고 결과 목록을 `employees`라는 이름으로 Model에 담습니다. 반환값 `employeelist`에는 `@ResponseBody`를 붙이지 않습니다. 이 문자열은 화면에 출력할 글자가 아니라 JSP를 찾기 위한 뷰 이름입니다.

앞에서 설정한 접두사와 접미사를 결합하면 다음 JSP가 선택됩니다.

```text
/WEB-INF/views/ + employeelist + .jsp
= /WEB-INF/views/employeelist.jsp
```

## 19.8 JSP에서 직원 목록 출력하기

파일: `src/main/webapp/WEB-INF/views/employeelist.jsp`

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>10번 부서 직원 목록</title>
    <style>
        body { font-family: sans-serif; margin: 40px; color: #24352d; }
        table { border-collapse: collapse; width: 100%; max-width: 680px; }
        th, td { border: 1px solid #cad7ce; padding: 12px 16px; text-align: left; }
        th { background: #edf6ef; }
        caption { text-align: left; margin-bottom: 12px; }
    </style>
</head>
<body>
    <h1>10번 부서 직원 목록</h1>
    <p>MyBatis로 EMP와 DEPT를 JOIN한 결과입니다.</p>

    <table>
        <caption>직원 이름과 소속 부서</caption>
        <thead>
            <tr>
                <th scope="col">직원 이름</th>
                <th scope="col">부서 번호</th>
                <th scope="col">부서 이름</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="dto" items="${employees}">
                <tr>
                    <td><c:out value="${dto.ename1}" /></td>
                    <td><c:out value="${dto.dno1}" /></td>
                    <td><c:out value="${dto.dname1}" /></td>
                </tr>
            </c:forEach>
            <c:if test="${empty employees}">
                <tr><td colspan="3">조회된 직원이 없습니다.</td></tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>
```

Jakarta Tags 3.0의 Core 태그 URI인 `jakarta.tags.core`를 사용합니다. 이 URI는 태그 라이브러리를 식별하는 이름이며 브라우저에서 여는 웹 주소가 아닙니다. [Jakarta Tags 3.0 안내](https://jakarta.ee/specifications/tags/3.0/)

`items="${employees}"`는 Controller가 Model에 넣은 목록입니다. `var="dto"`는 반복할 때마다 직원 한 명을 가리킵니다. `c:out`은 값을 출력하면서 기본적으로 HTML 특수 문자를 이스케이프합니다. 조회 결과가 없을 때는 빈 표만 보여 주는 대신 안내 문장을 표시합니다.

원본의 `out.println()` 스크립틀릿은 일반 HTML 문장으로 바꾸었습니다. 정적인 제목을 출력하기 위해 JSP에 Java 코드를 넣을 필요는 없습니다.

## 19.9 실행하고 결과 확인하기

### 접속 정보 지정

Windows PowerShell에서는 프로젝트 폴더로 이동한 뒤 다음과 같이 실행합니다. 아래 URL의 `XEPDB1`은 예시입니다. 실제 데이터베이스의 서비스 이름과 계정으로 바꾸세요. 비밀번호 자리에 예시 문구를 그대로 사용하지 않습니다.

```powershell
$env:DB_URL = 'jdbc:oracle:thin:@//localhost:1521/XEPDB1'
$env:DB_USERNAME = '실습계정명'
$env:DB_PASSWORD = '실제비밀번호'
.\gradlew.bat bootRun
```

macOS/Linux에서는 다음과 같이 실행합니다.

```bash
export DB_URL='jdbc:oracle:thin:@//localhost:1521/XEPDB1'
export DB_USERNAME='실습계정명'
export DB_PASSWORD='실제비밀번호'
./gradlew bootRun
```

Spring Tools에서 실행한다면 Run Configurations의 Environment에 같은 세 변수를 등록합니다. 터미널에 설정한 환경 변수는 이미 실행 중인 IDE에 자동 반영되지 않습니다.

### 브라우저 확인

먼저 `http://localhost:8081/`에 접속하면 다음 문구가 표시됩니다.

```text
MyBatis : 복잡한 JOIN 쿼리 결과 출력하기
```

이 화면은 애플리케이션과 Controller의 기본 응답을 확인하는 단계입니다. 직원 조회 SQL의 성공 여부는 다음 주소에서 확인합니다.

```text
http://localhost:8081/employee
```

19.3절의 데이터를 준비했다면 다음 결과를 예상할 수 있습니다.

| 직원 이름 | 부서 번호 | 부서 이름 |
|---|---:|---|
| CLARK | 10 | ACCOUNTING |
| KING | 10 | ACCOUNTING |
| MILLER | 10 | ACCOUNTING |

`SMITH`는 20번 부서이므로 포함되지 않습니다. 기존 EMP 데이터로 실습한다면 직원 수와 이름은 달라질 수 있습니다.

### 빌드 확인

애플리케이션을 종료한 뒤 Windows에서는 다음 명령을 실행합니다.

```powershell
.\gradlew.bat clean build
```

macOS/Linux에서는 다음 명령을 실행합니다.

```bash
./gradlew clean build
```

`BUILD SUCCESSFUL`은 빌드가 성공했다는 뜻입니다. 이 결과만으로 Oracle JOIN과 JSP 출력까지 확인한 것은 아닙니다. 반드시 `/employee`의 응답도 확인합니다. Initializr가 생성한 컨텍스트 로딩 테스트가 있으면 테스트 실행 시에도 데이터베이스 설정이 필요할 수 있습니다.

## 19.10 resultType과 resultMap 이해하기

이번 실습은 SQL의 별칭을 DTO 프로퍼티에 맞추었으므로 `resultType`만으로 매핑할 수 있습니다. 열 이름을 바꾸기 어렵거나 매핑 관계를 XML에서 명시하고 싶다면 `resultMap`을 사용할 수 있습니다.

두 방식의 차이를 확인하려면 **기존 UserDao.xml 전체를 아래 내용으로 교체**합니다. 같은 `id="getEmployee"`를 두 번 추가하지 않습니다. Java와 JSP는 그대로 둡니다.

파일: `src/main/resources/mybatis/mapper/UserDao.xml` (선택 실습용 대체본)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.study.springboot.jdbc.IDao">

    <resultMap id="employeeResultMap"
               type="com.study.springboot.jdbc.UserDTO">
        <result column="ename" property="ename1" />
        <result column="deptno" property="dno1" />
        <result column="dname" property="dname1" />
    </resultMap>

    <select id="getEmployee" resultMap="employeeResultMap">
        SELECT e.ename, d.deptno, d.dname
        FROM emp e
        JOIN dept d ON e.deptno = d.deptno
        WHERE d.deptno = 10
        ORDER BY e.empno
    </select>

</mapper>
```

`column`에는 조회 결과의 열 이름을, `property`에는 DTO의 프로퍼티 이름을 작성합니다. `resultMap` 방식에서도 결과는 동일한 `UserDTO`에 담깁니다. 따라서 **DTO와 resultMap은 서로 대체하는 개념이 아닙니다.** DTO는 결과를 담는 객체이고, resultMap은 그 객체에 값을 연결하는 규칙입니다.

원본의 “VO가 resultMap보다 빠르다”라는 설명은 이 둘의 역할을 혼동할 수 있으므로 사용하지 않습니다. `resultMap`은 `java.util.Map`과도 다릅니다. 단순한 평면 조회는 별칭과 `resultType`으로 간결하게 작성하고, 명시적인 연결이나 복잡한 객체 구조가 필요하면 `resultMap`을 선택합니다. [MyBatis 결과 매핑 문서](https://mybatis.org/mybatis-3/sqlmap-xml.html#Result_Maps)

## 19.11 자주 발생하는 문제

| 증상 | 확인할 내용 |
|---|---|
| `Invalid bound statement (not found)` | XML의 namespace와 인터페이스 전체 이름, select id와 메서드 이름, mapper-locations 경로를 확인합니다. |
| DTO 클래스를 찾을 수 없음 | resultType이 `com.study.springboot.jdbc.UserDTO`인지 확인합니다. `dto` 패키지와 혼용하지 않습니다. |
| 이름이 비어 있거나 부서 번호가 0으로 출력됨 | SQL 별칭과 DTO 프로퍼티가 일치하는지, Lombok setter가 생성되는지 확인합니다. |
| `ORA-00942` | 접속 계정에서 EMP·DEPT 테이블을 조회할 수 있는지 확인합니다. 다른 스키마 소유라면 적절한 권한과 스키마 지정이 필요합니다. |
| `ORA-00904` | EMPNO, ENAME, DEPTNO, DNAME 열이 실제 테이블에 존재하는지 확인합니다. |
| DB_URL 등의 값을 찾지 못함 | 애플리케이션을 실행한 터미널 또는 IDE 실행 설정에 환경 변수를 등록했는지 확인합니다. |
| 직원 목록이 비어 있음 | 10번 부서의 직원이 있는지, 데이터 입력 후 COMMIT했는지 확인합니다. |
| JSP가 표시되지 않음 | 파일이 `src/main/webapp/WEB-INF/views/employeelist.jsp`에 있는지, JSP 의존성과 뷰 설정이 올바른지 확인합니다. |
| 포트 8081 사용 중 | 이전 실습 서버를 종료하거나 server.port를 변경하고 변경한 포트로 접속합니다. |

`Downloading external resources is disabled`는 편집기의 DTD 다운로드 설정과 관련된 메시지입니다. Spring Tools의 `Window → Preferences → XML (Wild Web Developer)`에서 외부 DTD·XSD 다운로드 설정을 확인합니다. SQL을 바꾸어 해결할 문제가 아닙니다.

## 19.12 정리

JOIN 결과가 반드시 한 테이블과 동일한 구조일 필요는 없습니다. 화면에 필요한 열을 여러 테이블에서 조회하고 하나의 DTO로 표현할 수 있습니다.

이번 실습의 연결 관계를 다시 확인합니다.

| 단계 | 연결 기준 |
|---|---|
| EMP ↔ DEPT | DEPTNO |
| IDao ↔ UserDao.xml | namespace와 메서드/select id |
| 조회 결과 ↔ UserDTO | SQL 별칭 또는 resultMap |
| Controller ↔ JSP | Model 속성 이름 employees |
| JSP ↔ DTO 값 | ename1, dno1, dname1 프로퍼티 |

18장에서 SQL 실행 결과를 숫자로 받았다면, 이번 장에서는 JOIN 결과 여러 행을 DTO 목록으로 받아 화면에 출력했습니다. SQL, 매퍼, DTO, Model, JSP 사이에서 어떤 이름이 연결되는지 이해하는 것이 이 장의 핵심입니다.
