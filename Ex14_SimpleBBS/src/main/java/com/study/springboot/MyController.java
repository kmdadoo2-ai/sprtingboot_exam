package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.study.springboot.dao.ISimpleBbsDao;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyController 
{
    private final ISimpleBbsDao dao;

    @GetMapping("/")
    public String root() throws Exception 
    {
        // JdbcTemplate : SimpleBBS
        return "redirect:list";
    }

    @GetMapping("/list")
    public String userlistPage(Model model) 
    {
        model.addAttribute("list", dao.listDao());
        return "list";
    }

    @GetMapping("/view")
    public String view(HttpServletRequest request, Model model) 
    {
        String sId = request.getParameter("id");
        model.addAttribute("dto", dao.viewDao(sId));
        return "view";
    }

    @GetMapping("/writeForm")
    public String writeForm() 
    {
    	return "writeForm";
    }

    @PostMapping("/write")
    public String write(Model model, HttpServletRequest request) 
    {
        dao.writeDao(request.getParameter("writer"),
                	 request.getParameter("title"),
                	 request.getParameter("content"));
        return "redirect:list";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, Model model) 
    {
        dao.deleteDao(request.getParameter("id"));
        return "redirect:list";
    }
}