# 19장 편집 및 검토 기록

작성일: 2026-10-07

## 원고와 삽입 위치

- 원고: `chapter19-mybatis-complex.md`
- 제목: 19장 MyBatis로 JOIN 조회 결과 출력하기
- 위치: MyBatis 실행 결과를 숫자로 받는 18장 다음
- 형식: 위키독스에 붙여 넣을 Markdown
- 기존 18장 본문은 제공되지 않아 문장 단위의 연속성은 대조하지 않았다. 작업 폴더의 `Ex18_MyBatis_ResultNum` Controller, 매퍼, Gradle 설정을 확인해 연결 문단을 작성했다.
- 작업 폴더의 `Ex19_SqlLog`는 첨부 PDF의 `Ex19_MyBatis_Complex`와 다른 실습이므로 수정하지 않았다. 기존 책에서 SQL 로그 장에 19번을 사용 중이라면 최종 목차에서 번호 조정이 필요하다.

## 첨부 PDF 분석

자료: `E:/Users/kmdadoo/Downloads/19.MyBatis_Complex.pdf` (7페이지)

PDF 텍스트와 각 페이지에 포함된 코드/화면 이미지 10개를 모두 확인했다.

| PDF 페이지 | 내용 | 개정 원고 |
|---|---|---|
| 1 | 프로젝트 생성, Java 17, WAR, 의존성 선택 | 19.2, Java 21 및 기존 실습 환경 반영 |
| 2 | Gradle 의존성, Oracle/MyBatis 설정 | 19.2 및 19.4 |
| 3 | 패키지와 JSP 배치 | 19.4 |
| 4 | UserDTO 및 IDao | 19.5 |
| 5 | EMP/DEPT JOIN, 별칭, 매핑 관련 주석 | 19.6 및 19.10 |
| 6 | Controller, employees 모델, /employee | 19.7 |
| 7 | employeelist.jsp 반복 출력 | 19.8 |

## 보완 사항

- 초보자의 재현을 돕기 위해 최소 EMP/DEPT 테이블과 샘플 데이터를 추가했다. 기존 테이블을 삭제하는 구문은 넣지 않았다.
- 원본의 HR 계정과 EMP/DEPT 테이블 조합을 당연한 전제로 두지 않고, 실제 테이블을 가진 실습 스키마를 사용하도록 설명했다.
- 원본 주석 예제의 `getemployee`/`getEmployee`, `dto.UserDTO`/`jdbc.UserDTO` 불일치를 통일했다.
- IDao의 Mapper import를 포함했다.
- 필드 주입을 생성자 주입으로 바꾸고 ArrayList 반환형을 List로 변경했다.
- 암시적 조인을 명시적 JOIN으로, JSP의 javax 계열 JSTL을 Jakarta Tags로 변경했다.
- 원본에 없는 정렬 조건을 추가해 예상 결과 순서를 일정하게 했다.
- resultMap, DTO, java.util.Map을 구분하고 근거 없는 성능 우열 문장을 삭제했다.
- SQL 결과의 별칭과 DTO 필드명은 원본과 동일하게 보존했다.

## 검토 결과와 한계

- Java 코드 5개 모두 package, import, 전체 클래스/인터페이스를 포함하는지 확인했다.
- 기본 매퍼 XML과 선택 실습용 resultMap XML 모두 XML 파싱 검사를 통과했다. DTD 유효성 검사는 별도로 실행하지 않았다.
- 두 SELECT를 메모리 SQLite에 준비한 동일 샘플 데이터로 실행해 CLARK, KING, MILLER의 3행과 예상 순서를 확인했다. 이는 JOIN 논리 확인이며 Oracle 호환성/연동 테스트가 아니다.
- namespace, 메서드명, DTO 패키지, 별칭, Model 이름, JSP 접근 이름을 대조했다.
- Spring Boot, MyBatis Starter, Jakarta Tags 공식 문서를 확인했다. 관련 링크는 본문에 포함했다.
- Gradle 빌드, 실제 Oracle 접속, Spring Boot 시작, JSP 렌더링은 이번 원고 작성 과정에서 실행하지 않았다. 본문의 화면 결과는 샘플 데이터 기준 예상 결과이다.
- 출판 전 대상 Oracle 환경에서 `clean build`와 `/employee` 출력 확인이 필요하다.
