package com.study.springboot.jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class MyController 
{
	private final MemberService memberService;

    @GetMapping("/")
    public String root() throws Exception {
        return "menu";
    }

    @GetMapping("/insert")
    public String insert(@RequestParam String username, Model model) 
    {
    	// 데이터 3개 정도 추가하고 테스트
    	Member member = Member.builder()
    			.username(username)
    			.createDate(LocalDate.now())
    			.build();
   		Member result = memberService.insert(member);
   		model.addAttribute("member", result);

        return "insert";
    }

    @GetMapping("/select")
    public String select(@RequestParam Long id, Model model) 
    {
        Optional<Member> result = memberService.select(id);
        if (result.isPresent())
        {
       		model.addAttribute("member", result.get());
        }
        else
        {
       		model.addAttribute("member", null);
        }

   		return "select";
    }

    @GetMapping("/selectAll")
    public String selectAll(Model model) 
    {
        List<Member> result = memberService.selectAll();
   		model.addAttribute("members", result);

   		return "selectAll";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) 
    {
        memberService.delete(id);
        
        return "delete";
    }

    @GetMapping("/update")
    public String update(Member member, Model model) 
    {
    	member.setCreateDate(LocalDate.now());
        Member result = memberService.update(member);
   		model.addAttribute("member", result);

        return "update";
    }

}
