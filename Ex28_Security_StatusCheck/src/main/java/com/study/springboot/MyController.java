package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController 
{
    @GetMapping("/")
    @ResponseBody
    public String root() throws Exception 
    {
    	return "Security-StatusCheck (3)";
    }

    @GetMapping("/guest/welcome")
    public String welcome1() 
    {
        return "guest/welcome1";
    }

    @GetMapping("/member/welcome")
    public String welcome2() 
    {
        return "member/welcome2";
    }

    @GetMapping("/admin/welcome")
    public String welcome3() 
    {
        return "admin/welcome3";
    }
    
    @RequestMapping(value = "/loginForm", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginForm() 
    {
        return "security/loginForm";
    }
}