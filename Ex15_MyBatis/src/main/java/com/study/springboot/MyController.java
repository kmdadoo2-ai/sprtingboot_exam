package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.IMyUserDao;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyController 
{
    private final IMyUserDao userDao;

    @GetMapping("/")
    @ResponseBody
    public String root() throws Exception 
    {
        return "MyBatis 사용하기";
    }

    @GetMapping("/user")
    public String userlistPage(Model model) 
    {
        model.addAttribute("users", userDao.list());
        return "userlist";
    }
}
