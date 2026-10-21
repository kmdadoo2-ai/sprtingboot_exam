package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController 
{
    @GetMapping("/")
    @ResponseBody
    public String root() throws Exception 
    {
        return "ValidationUtils (2)";
    }

    @GetMapping("/insertForm")
    public String insert1() 
    {
        return "createPage";
    }

    @GetMapping("/create")
    public String insert2(@ModelAttribute("dto") ContentDto contentDto,
                                          BindingResult result) 
    {
        String page = "createDonePage";
        System.out.println(contentDto);

        ContentValidator validator =  new ContentValidator();
        validator.validate(contentDto, result);
        if (result.hasErrors()) {
        	System.out.println("getAllErrors : " + result.getAllErrors());

            if (result.getFieldError("writer") != null) {
                System.out.println("1: " + result.getFieldError("writer").getCode());
            }
            if (result.getFieldError("content") != null) {
                System.out.println("2: " + result.getFieldError("content").getCode());
            }
            
            page = "createPage";
        }

        return page;
    }
}
