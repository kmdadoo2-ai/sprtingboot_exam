package com.study.springboot.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration   
public class Config {
    // 빈(bean) : Spring이 loC 방식으로 관리하는 객체
    // 빈 팩토리 (BeanFactory) : 스프링의 loCt 담당하는 핵심 컨테이너
    // 어플리케이션 컨텍스트(Applicationcontext) : 빈 팩토리를 확장한 loC 컨테이너

    @Bean 
    Member member1() {
        // Setter Injection (Setter 메서드를 이용한 의존성 주입)
        Member member1 = new Member();
        member1.setName("홍길동");
        member1.setNickname("도사");
        member1.setPrinter(new PrinterA());

        return member1;
    }

    @Bean(name = "hello") 
    Member member2() {
        // Constructor Injection (생성자를 이용한 의존성 주입)
        return new Member(
                "전우치",
                "도사",
                new PrinterA()
        );
    }

    @Bean 
    PrinterA printerA() {
        return new PrinterA();
    }

    @Bean 
    PrinterB printerB() {
        return new PrinterB();
    }
}
