package com.study.springboot.jpa;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity(name="JPAMEMBER01")
public class Member 
{
    @Id
    @GeneratedValue
    private Long id;
    private String username;
	@Column(name="create_date")
	private LocalDate createDate;
}

// 스프링 부트는 3.2부터 더 이상 바이트코드를 파싱할때
// 파라미터 이름을 추론하지 않는다고 한다.
// 그리고 의존성 주입 또는 속성을 바인딩 할 때는 -parameters 옵션으로
// 컴파일 했는지 꼭 확인하라고 한다.

// 이클립스에서는 다음과 같이 설정하면 된다.
// 상단 메뉴 - Window - Preferences - Java -
// Store information about method parameters (usable via reflection) 체크