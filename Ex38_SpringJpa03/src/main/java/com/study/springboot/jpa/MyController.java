package com.study.springboot.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/selectByNameLike")
    public String selectByNameLike(@RequestParam("name") String search,
			   @RequestParam("page") String page,
			   Model model) 
	{
		System.out.println("***"+ search +"***");
		System.out.println("***"+ page +"***");
		
		String name = search + "%";
		Sort sort = Sort.by(Sort.Order.desc("name"));
		int nPage = Integer.parseInt(page) - 1;
		
		// 페이지는 0부터
		Pageable pageable = PageRequest.ofSize(5).withPage(nPage).withSort(sort);
		
		Page<Member> result = memberService.selectNameLike(name, pageable);
		List<Member> content = result.getContent();
		long totalElements = result.getTotalElements();
		int  totalPages    = result.getTotalPages();
		int  size          = result.getSize();
		int  pageNumber    = result.getNumber() + 1;	// 0부터 시작하므로
		int  numberOfElements = result.getNumberOfElements(); // content의 개수
		
		model.addAttribute("members", content);
		model.addAttribute("totalElements", totalElements);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("numberOfElements", numberOfElements);
		
		return "select_name_list";
	}
}
