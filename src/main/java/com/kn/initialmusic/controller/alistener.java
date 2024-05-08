package com.kn.initialmusic.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class alistener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();

        //获取在线人数
        Integer onlineNum = (Integer) servletContext.getAttribute("onlineNum");
        //在线人数加一
        servletContext.setAttribute("onlineNum", ++onlineNum);

    }


    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();

        //获取在线人数
        Integer onlineNum = (Integer) servletContext.getAttribute("onlineNum");

        //在线人数减一
        servletContext.setAttribute("onlineNum", --onlineNum);

    }
}
