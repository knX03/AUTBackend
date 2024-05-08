package com.kn.initialmusic.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    @Autowired
    public GenerateIDService generateIDService;

    @Test
    void findById(){
        String generateid = generateIDService.generateUserID();
        System.out.println(generateid);
    }

    @Test
    public void serviceTest(){
        System.out.println("hello world!");

    }
}
