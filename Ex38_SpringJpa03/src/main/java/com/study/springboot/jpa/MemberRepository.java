package com.study.springboot.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> 
{
//  List<Member> findByNameLike(String keyword, Pageable pageable);
	Page<Member> findByNameLike(String keyword, Pageable pageable);
}
