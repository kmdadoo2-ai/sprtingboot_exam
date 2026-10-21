package com.study.springboot;

import java.util.HashMap;
import java.util.Map;

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
        // MyBatis : ResultNum
        return "redirect:list";
    }

    @GetMapping("/list")
    public String userlistPage(Model model) 
    {
        model.addAttribute("list", dao.listDao());

        int nTotalCount = dao.articleCount();
        System.out.println("Count : " + nTotalCount);

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
    public String write(HttpServletRequest request, Model model) 
    {
        String sName = request.getParameter("writer");
        String sTitle = request.getParameter("title");
        String sContent = request.getParameter("content");

        Map<String, String> map = new HashMap<String, String>();
        map.put("item1", sName);
        map.put("item2", sTitle);
        map.put("item3", sContent);

        int nResult = dao.writeDao(map);
        System.out.println("Write : " + nResult);

        return "redirect:list";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, Model model) 
    {
        String sId = request.getParameter("id");
        int nResult = dao.deleteDao(sId);
        System.out.println("Delete : " + nResult);

        return "redirect:list";
    }
}
