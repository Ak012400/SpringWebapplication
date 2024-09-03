package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LogicController {
    
    @GetMapping("/welcome")
    public ModelAndView logic() {
      ModelAndView mView=new ModelAndView();
      mView.setViewName("Welcome");
        return mView;
    }
}
