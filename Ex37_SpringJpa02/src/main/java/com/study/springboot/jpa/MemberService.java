package com.study.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class MemberService
{
	private final MemberRepository memberRepository;

    public void insert()
    {
		Member member;
    	
    	member = Member.builder().name("이순신").email("test1@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("강감찬").email("test2@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("을지문덕").email("test3@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("계백").email("test4@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("김유신").email("test5@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("연개소문").email("test6@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("양만춘").email("test7@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("김종서").email("test8@test.com").build();
        memberRepository.save(member);

        member = Member.builder().name("최영").email("test9@test.com").build();
        memberRepository.save(member);

    }
    
    public List<Member> selectAll() 
    {
        return memberRepository.findAll();
    }

    public Optional<Member> selectId(Long search) 
    {
        Optional<Member> member = memberRepository.findById(search);
        return member;
    }

    public Optional<Member> selectName(String search) 
    {
    	Optional<Member> member = memberRepository.findByName(search);
        return member;
    }

    public Optional<Member> selectEmail(String search) 
    {
    	Optional<Member> member = memberRepository.findByEmail(search);
        return member;
    }

    public List<Member> selectNameLike(String search) 
    {
    	List<Member> member = memberRepository.findByNameLike(search);
        return member;
    }

    public List<Member> selectNameLikeNameDesc(String search) 
    {
    	List<Member> member = memberRepository.findByNameLikeOrderByNameDesc(search);
        return member;
    }

    public List<Member> selectNameLike(String search, Sort sort) 
    {
    	List<Member> member = memberRepository.findByNameLike(search, sort);
        return member;
    }
}

