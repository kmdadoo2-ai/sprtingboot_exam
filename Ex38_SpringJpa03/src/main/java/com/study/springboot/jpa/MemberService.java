package com.study.springboot.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class MemberService
{
	private final MemberRepository memberRepository;
    
//  public List<Member> selectNameLike(String search, Pageable pageable) 
//  {
//  	List<Member> member = memberRepository.findByNameLike(search, pageable);
//      return member;
//  }

	public Page<Member> selectNameLike(String search, Pageable pageable) 
	{
		Page<Member> member = memberRepository.findByNameLike(search, pageable);
		return member;
	}
}

