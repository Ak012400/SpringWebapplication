package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
       // templateResolver.setCacheable(false);
        templateResolver.setPrefix("classpath:/Admin/");
        templateResolver.setSuffix(".html");
        // SetOrder required to mention the order of the view engines, when using multiple view engines.
        // templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver1() {
        SpringResourceTemplateResolver templateResolver1 = new SpringResourceTemplateResolver();
        //templateResolver1.setCacheable(false);
        templateResolver1.setPrefix("classpath:/Customers/");
        templateResolver1.setSuffix(".html");
        // Required if view files are in multiple locations
        templateResolver1.setCheckExistence(true);
        return templateResolver1;
    }
    @Bean
    public SpringResourceTemplateResolver templateResolver2() {
        SpringResourceTemplateResolver templateResolver1 = new SpringResourceTemplateResolver();
        //templateResolver1.setCacheable(false);
        templateResolver1.setPrefix("classpath:/templates/");
        templateResolver1.setSuffix(".html");
        // Required if view files are in multiple locations
        templateResolver1.setCheckExistence(true);
        return templateResolver1;
    }

    @Bean
    public 
 SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        // Other configurations for the template engine
        templateEngine.addTemplateResolver(templateResolver());
        templateEngine.addTemplateResolver(templateResolver1());
        templateEngine.addTemplateResolver(templateResolver2());
        return templateEngine;
    }
    @Bean
    public ThymeleafViewResolver viewResolver() {
      ThymeleafViewResolver viewResolver1=new ThymeleafViewResolver();
      viewResolver1.setTemplateEngine(templateEngine());
      return viewResolver1;
    }
}
