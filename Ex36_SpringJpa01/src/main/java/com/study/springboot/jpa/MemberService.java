package com.study.springboot.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class MemberService
{
	private final MemberRepository memberRepository;

    public Member insert(Member member) 
    {
    	Member returnMember = memberRepository.save(member);
    	return returnMember;
    }

    public Optional<Member> select(Long id) 
    {
        Optional<Member> member = memberRepository.findById(id);
        return member;
    }

    public List<Member> selectAll() 
    {
        return memberRepository.findAll();
    }

    public void delete(Long id) 
    {
        memberRepository.deleteById(id);
    }

    public Member update(Member member) 
    {
    	// insert 메서드에서 사용한 방법과 똑같다.
    	// 키 값과 같은 값이 있으면 업데이트, 없으면 인서트
    	Member returnMember = memberRepository.save(member);
    	return returnMember;
    }
}

