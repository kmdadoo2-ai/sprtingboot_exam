package com.study.springboot.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class MemberService
{
	private final MemberRepository memberRepository;
	
    public List<Member> selectMembers1(String search) 
    {
        List<Member> member = memberRepository.findMembers(search);
        return member;
    }
    
	public List<Member> selectMembers2(String search, Sort sort) 
    {
        List<Member> member = memberRepository.findMembers(search, sort);
        return member;
    }

    public Page<Member> selectMembers3(String search, Pageable pageable) 
    {
        Page<Member> member = memberRepository.findMembers(search, pageable);
        return member;
    }

    public List<Member> selectMembers4(String search) 
    {
        List<Member> member = memberRepository.findMembersNative(search);
        return member;
    }

}

