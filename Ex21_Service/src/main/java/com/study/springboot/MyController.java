package com.study.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.study.springboot.service.ISimpleBbsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor    
public class MyController 
{
//    private final ISimpleBbsDao dao;    
    
    private final ISimpleBbsService bbs;  

    @GetMapping("/")
    public String root() throws Exception 
    {
        // Service vs DAO
        return "redirect:list";
    }

    @GetMapping("/list")
    public String userlistPage(Model model) 
    {
        //model.addAttribute("list", dao.listDao());   
        model.addAttribute("list", bbs.list());    

        int nTotalCount = bbs.count();
        System.out.println("Count : " + nTotalCount);

        return "list";
    }

    @GetMapping("/view")
    public String view(HttpServletRequest request, Model model) 
    {
        String sId = request.getParameter("id");
        model.addAttribute("dto", bbs.view(sId));

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

        int nResult = bbs.write(map);
        System.out.println("Write : " + nResult);

        return "redirect:list";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request, Model model) 
    {
        String sId = request.getParameter("id");
        int nResult = bbs.delete(sId);
        System.out.println("Delete : " + nResult);

        return "redirect:list";
    }
}
