package com.kn.initialmusic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@SuppressWarnings("deprecation")
@SpringBootConfiguration
public class SpringMVCConfig {
    @Autowired
    private FilterConfig filterConfig;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(filterConfig).addPathPatterns("/**");
    }
}
