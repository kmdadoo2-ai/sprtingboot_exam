package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.IDao;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor 
public class MyController 
{
    private final IDao userDao;

    @GetMapping("/")
    @ResponseBody
    public String root() throws Exception
    {
        return "MyBatis : 복잡한 JOIN 쿼리 결과 출력하기";
    }

    @GetMapping("/employee")
    public String employeeList(Model model) 
    {
        model.addAttribute("employees",  userDao.getEmployee());
        return "employeelist";
    }
}
