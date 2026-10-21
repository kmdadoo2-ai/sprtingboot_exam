package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

@Controller
public class MyController 
{
    @GetMapping("/")
    @ResponseBody
    public String root() throws Exception 
    {
    	return "Valid_Annotation (4)";
    }

    @GetMapping("/insertForm")
    public String insert1() 
    {
        return "createPage";
    }

    @GetMapping("/create")
    public String insert2(@ModelAttribute("dto") @Valid ContentDto contentDto,
            BindingResult result) 
    {
        String page = "createDonePage";
        System.out.println(contentDto);
        
//        ContentValidator validator =  new ContentValidator();
//        validator.validate(contentDto, result);
        if (result.hasErrors()) {
//            if (result.getFieldError("writer") != null) {
//                System.out.println("1: " + result.getFieldError("writer")
//                                .getCode());
//            }
//            if (result.getFieldError("content") != null) {
//                System.out.println("2: " + result.getFieldError("content")
//                                .getCode());
//            }
        	if (result.getFieldError("writer") != null) {

                System.out.println(
                        "1: "
                        + result.getFieldError("writer")
                                .getDefaultMessage()
                );
            }

            if (result.getFieldError("content") != null) {

                System.out.println(
                        "2: "
                        + result.getFieldError("content")
                                .getDefaultMessage()
                );
            }

            page = "createPage";
        }

        return page;
    }

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) 
//    {
//        binder.setValidator(new ContentValidator());
//    }
}
