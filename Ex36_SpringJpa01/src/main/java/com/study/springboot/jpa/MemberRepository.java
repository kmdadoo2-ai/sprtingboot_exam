package com.study.springboot.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> 
{
    // 제네릭타입 : long 이 아니라 Long으로 작성.
    // 기본적인 Create, Read, Update, Delete 자동으로 생성
}

