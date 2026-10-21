package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.MyUserDAO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyController 
{
    private final MyUserDAO userDao;

    @GetMapping("/")
    public @ResponseBody String root() throws Exception 
    {
        return "JdbcTemplate 사용하기";
    }

    @GetMapping("/user")
    public String userlistPage(Model model) 
    {
        model.addAttribute("users", userDao.list());
        return "userlist";
    }
}