package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Singer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {

    @Autowired
    public GenerateIDService generateIDService;

    @Autowired
    private SingerService singerService;

    @Test
    void findById() {
        String generateid = generateIDService.generateUserID();
        System.out.println(generateid);
    }

    @Test
    public void serviceTest() {
        System.out.println("hello world!");

    }

    @Test
    public void getSInger() {
        Result singer = singerService.getSingerByUser("46540");
        System.out.println(singer);
    }
}
