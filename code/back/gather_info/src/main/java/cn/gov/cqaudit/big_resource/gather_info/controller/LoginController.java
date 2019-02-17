package cn.gov.cqaudit.big_resource.gather_info.controller;

import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller 
public class LoginController {

    @RequestMapping(value = "/login.html") 
    public String loginPage() { 
        return "login"; 
    }

    @RequestMapping(value = "/loginCheck.html") 
    public ModelAndView loginCheck(HttpServletRequest request, LoginCommand loginCommand) { 
            return new ModelAndView("main"); 
    } 
}