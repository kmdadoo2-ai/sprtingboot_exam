# 예제로 배우는 스프링 부트 입문 — 개정판 실습

Java 21과 Spring Boot 4.x를 기반으로 기본 개념부터 웹 개발, 데이터베이스 연동, 보안, JPA까지 단계적으로 학습하는 예제 저장소입니다.

『예제로 배우는 스프링 부트 입문』을 바탕으로 원저자의 허락을 받아 진행하는 위키독스 개정 작업의 실습 코드를 담고 있습니다.

**이재환 · 김현무 지음**

## 개발 환경

아래 버전은 저장소의 빌드 설정 기준입니다.

| 항목 | 사용 환경 |
| --- | --- |
| Java | JDK 21 |
| Spring Boot | 4.1.1 |
| 빌드 도구 | Gradle Wrapper 9.7.1 / 9.8.1 — 프로젝트별 설정 사용 |
| 웹 | Spring MVC, JSP, Jakarta Servlet, JSTL |
| 데이터베이스 | Oracle |
| 데이터 접근 | JdbcTemplate, MyBatis, JPA, Spring Data JPA |
| 보안 | Spring Security |
| 기타 | Lombok, WebJars, 파일 업로드 |
| IDE | Eclipse / Spring Tools, IntelliJ IDEA, VS Code |
| 인코딩 | UTF-8 |

연관 라이브러리는 각 프로젝트의 Spring Boot 의존성 관리 설정을 따릅니다. 개별 구성은 해당 폴더의 `build.gradle`을 확인하세요.

## 실습 목록

각 폴더는 **독립 프로젝트**입니다. 루트에서 전체 예제를 한 번에 실행하는 Gradle 멀티 프로젝트 구조는 아닙니다. 현재 `HelloWorld`와 번호가 붙은 실습 37개가 있으며, `Ex34`, `Ex35` 폴더는 포함되어 있지 않습니다.

| 폴더 | 학습 내용 |
| --- | --- |
| [HelloWorld](./HelloWorld/) | 스프링 부트 애플리케이션 기본 구성 |
| [Ex01_DI](./Ex01_DI/) | 일반 Java 코드로 이해하는 의존성과 DI |
| [Ex02_JavaCodeDI](./Ex02_JavaCodeDI/) | Java 설정을 이용한 의존성 주입 |
| [Ex03_AnnotationDI](./Ex03_AnnotationDI/) | 애너테이션을 이용한 의존성 주입 |
| [Ex04_WebBasic](./Ex04_WebBasic/) | 웹 프로젝트 기본 구성 |
| [Ex05_JspUse](./Ex05_JspUse/) | JSP 연결과 뷰 출력 |
| [Ex06_Model](./Ex06_Model/) | Model을 이용한 데이터 전달 |
| [Ex07_Form](./Ex07_Form/) | 폼 데이터와 요청 파라미터 처리 |
| [Ex08_Lombok](./Ex08_Lombok/) | Lombok 활용 |
| [Ex09_Validator](./Ex09_Validator/) | Validator를 이용한 검증 |
| [Ex10_ValidationUtils](./Ex10_ValidationUtils/) | ValidationUtils 활용 |
| [Ex11_Valid_initBinder](./Ex11_Valid_initBinder/) | @Valid와 @InitBinder 활용 |
| [Ex12_Valid_Annotation](./Ex12_Valid_Annotation/) | 검증 애너테이션 활용 |
| [Ex13_JdbcTemplate](./Ex13_JdbcTemplate/) | JdbcTemplate으로 데이터베이스 접근 |
| [Ex14_SimpleBBS](./Ex14_SimpleBBS/) | 간단한 게시판 구현 |
| [Ex15_MyBatis](./Ex15_MyBatis/) | MyBatis 기본 연동 |
| [Ex16_MyBatis_SimpleBBS](./Ex16_MyBatis_SimpleBBS/) | MyBatis 기반 게시판 |
| [Ex17_MyBatis_Param](./Ex17_MyBatis_Param/) | MyBatis 파라미터 전달 |
| [Ex18_MyBatis_ResultNum](./Ex18_MyBatis_ResultNum/) | SQL 실행 결과 건수 확인 |
| [Ex19_MyBatis_Complex](./Ex19_MyBatis_Complex/) | JOIN 조회와 결과 매핑 |
| [Ex20_SqlLog](./Ex20_SqlLog/) | SQL 로그 확인 |
| [Ex21_Service](./Ex21_Service/) | 서비스 계층 분리 |
| [Ex22_TransactionX](./Ex22_TransactionX/) | 트랜잭션 미적용 동작 확인 |
| [Ex23_TransactionManager](./Ex23_TransactionManager/) | 트랜잭션 매니저 활용 |
| [Ex24_TransactionTemplate](./Ex24_TransactionTemplate/) | TransactionTemplate 활용 |
| [Ex25_Transaction_Propagation](./Ex25_Transaction_Propagation/) | 트랜잭션 전파 |
| [Ex26_Security](./Ex26_Security/) | Spring Security 기본 설정 |
| [Ex27_Security_LoginForm](./Ex27_Security_LoginForm/) | 사용자 정의 로그인 화면 |
| [Ex28_Security_StatusCheck](./Ex28_Security_StatusCheck/) | 로그인 상태와 인증 실패 처리 |
| [Ex29_Security_taglibs](./Ex29_Security_taglibs/) | JSP 보안 태그 활용 |
| [Ex30_Security_Database](./Ex30_Security_Database/) | 데이터베이스 기반 인증 |
| [Ex31_WebJars](./Ex31_WebJars/) | WebJars로 프런트엔드 라이브러리 관리 |
| [Ex32_FileUpload](./Ex32_FileUpload/) | 단일·다중 파일 업로드 |
| [Ex33_JPA](./Ex33_JPA/) | JPA 매핑, CRUD, JPQL 콘솔 예제 |
| [Ex36_SpringJpa01](./Ex36_SpringJpa01/) | JpaRepository 기반 기본 CRUD |
| [Ex37_SpringJpa02](./Ex37_SpringJpa02/) | 메서드 이름으로 쿼리 생성 및 정렬 |
| [Ex38_SpringJpa03](./Ex38_SpringJpa03/) | Pageable과 Page를 이용한 페이징 |
| [Ex39_SpringJpa04](./Ex39_SpringJpa04/) | @Query, JPQL, 네이티브 SQL 활용 |

## 빠른 실행

JDK 21을 설치하고 `JAVA_HOME`, IDE의 프로젝트 JDK와 Gradle JVM을 확인합니다. 저장소를 내려받은 뒤 원하는 실습 폴더로 이동하세요. Gradle은 프로젝트에 포함된 Wrapper를 사용하므로 별도로 설치할 필요가 없습니다. 최초 실행에는 배포 파일과 의존성을 내려받기 위한 인터넷 연결이 필요합니다.

데이터베이스 준비 없이 시작하려면 `Ex05_JspUse`를 실행하세요.

**Windows PowerShell**

```powershell
java -version
cd Ex05_JspUse
.\gradlew.bat bootRun
```

**macOS / Linux**

```bash
java -version
cd Ex05_JspUse
chmod +x gradlew
./gradlew bootRun
```

시작 로그를 확인한 후 브라우저에서 접속합니다.

- [http://localhost:8081/](http://localhost:8081/) — `JSP in Gradle` 응답
- [http://localhost:8081/test1](http://localhost:8081/test1) — JSP 뷰
- [http://localhost:8081/test2](http://localhost:8081/test2) — 하위 경로의 JSP 뷰

종료하려면 터미널에서 `Ctrl+C`를 누릅니다. 대부분의 웹 예제가 같은 `8081` 포트를 사용하므로 다른 예제를 실행하기 전에 기존 애플리케이션을 종료하세요. 프로젝트마다 제공하는 URL은 컨트롤러 매핑을 확인합니다.

### IDE에서 실행

1. 실습 폴더를 Gradle 프로젝트로 가져옵니다.
2. JDK 21을 지정하고 Gradle 의존성을 동기화합니다.
3. `@SpringBootApplication`이 선언된 클래스의 `main()`을 실행합니다.

Lombok 예제는 IDE에서도 Lombok을 인식하도록 설정해야 합니다. Eclipse / Spring Tools에서는 Lombok의 IDE 설치 여부도 확인하세요.

### 실행 방식이 다른 예제

- **Ex01_DI**: 일반 Java 프로젝트입니다. 저장소 루트에서 `java Ex01_DI/src/UnderstandDI.java`로 실행하거나 IDE에서 `UnderstandDI.main()`을 실행합니다.
- **Ex33_JPA**: `exam1`부터 `exam4`까지 각 실습 클래스의 `main()`을 IDE에서 실행합니다. `bootRun`으로 실행하는 웹 예제가 아닙니다. 실행 전 `src/main/resources/META-INF/persistence.xml`의 접속 설정, 매핑 클래스와 스키마 설정을 해당 실습에 맞게 확인합니다.

## Oracle 데이터베이스 준비

데이터베이스를 사용하는 예제는 Oracle 인스턴스와 실습용 계정, 필요한 테이블 및 시퀀스를 준비해야 합니다.

1. 해당 프로젝트의 `src/main/resources/application.properties`에서 데이터소스 설정을 확인합니다.
2. 접속 주소, 사용자 이름, 비밀번호를 자신의 환경에 맞게 지정합니다. Spring Boot 예제는 `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` 환경 변수로도 재정의할 수 있습니다.
3. [study계정1.sql](./study계정1.sql)을 참고하여 필요한 SQL 구간만 실행합니다.
4. JPA 예제는 테이블 생성 방식과 엔티티 매핑도 확인합니다. `Ex33_JPA`는 별도의 `persistence.xml` 설정을 사용합니다.

SQL 파일에는 `myuser`, `simple_bbs`, `emp`, `dept`, 트랜잭션 실습 테이블, `user_list`, `JPAMEMBER03` 등의 정의가 포함되어 있습니다. **`DROP TABLE` 구문이 있으므로 기존 데이터가 있는 환경에서 파일 전체를 무조건 실행하지 마세요.** 실습 전용 데이터베이스에서 필요한 테이블과 초기 데이터를 준비하세요.

JPA의 `create` 또는 `create-drop` 설정도 매핑된 기존 테이블과 데이터를 삭제할 수 있으므로 실행 전에 확인하세요. 실제 운영 계정의 비밀번호는 Git에 저장하지 마세요.

## 빌드와 테스트

실행할 **개별 Gradle 프로젝트 폴더**에서 사용합니다.

| 작업 | Windows PowerShell | macOS / Linux |
| --- | --- | --- |
| 테스트 | `.\gradlew.bat test` | `./gradlew test` |
| 빌드 | `.\gradlew.bat build` | `./gradlew build` |
| 웹 애플리케이션 실행 | `.\gradlew.bat bootRun` | `./gradlew bootRun` |

데이터베이스 연동 프로젝트의 컨텍스트 로딩 테스트는 Oracle 연결과 스키마 준비가 필요할 수 있습니다. `Ex33_JPA`는 위의 별도 실행 안내를 따릅니다.

## 실습 시 확인할 사항

| 증상 | 확인할 내용 |
| --- | --- |
| `8081` 포트 사용 중 | 이전 실습을 종료하거나 `server.port` 변경 |
| Oracle 연결 실패 | DB 실행 상태, 접속 주소와 서비스명, 계정 정보 확인 |
| 테이블 또는 시퀀스 오류 | 접속 계정과 필요한 DDL 실행 여부 확인 |
| JSP 페이지를 찾을 수 없음 | 컨트롤러 반환값, 뷰 경로, JSP 파일 위치 확인 |
| Lombok 메서드를 IDE에서 인식하지 못함 | Gradle 동기화 및 IDE Lombok 설정 확인 |
| 파일 업로드 실패 | 크기 제한과 실행 디렉터리의 `upload` 폴더 쓰기 권한 확인 |

`Ex32_FileUpload`는 실행 디렉터리를 기준으로 `upload` 폴더를 생성합니다. 현재 설정은 파일당 최대 `2MB`, 요청당 최대 `20MB`입니다.

## 문서 범위

이 README는 저장소의 코드와 설정을 읽어 작성했습니다. 모든 프로젝트의 빌드, Oracle 연동, 브라우저 동작을 일괄 검증했다는 의미는 아닙니다. 실습 코드는 학습 목적이며 예제별 설정과 실행 결과를 확인하면서 진행하세요.
