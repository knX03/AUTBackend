package com.kn.initialmusic;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kn.initialmusic.mapper.UserMapper;
import com.kn.initialmusic.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.kn.initialmusic.util.RedisConstants.LOGIN_USER_KEY;

@SpringBootTest
public class redisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testS() throws JsonProcessingException {
        User user1 = new User();
        user1.setUser_ID("1231923");
        user1.setUser_Name("John");
        String users = mapper.writeValueAsString(user1);

        //redisTemplate.opsForValue().set("user", "u1");
        stringRedisTemplate.opsForValue().set("user", users);
        String user = stringRedisTemplate.opsForValue().get("user");
        User user2 = mapper.readValue(user, User.class);
        System.out.println("user=" + user2);
    }

    @Test
    void testS2() throws JsonProcessingException {
        /*HashMap<String, String> map = new HashMap<>();
        map.put("bbb", "111");
        map.put("ccc", "111");
        map.put("ddd", "111");
        String[] s = {"bbb", "ccc"};
        Long delete = stringRedisTemplate.opsForHash().delete("aaaa", s);
        System.out.println(delete);*/

/*        //删除hash数据
        String tokenKey = "aaaa";
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(tokenKey);
        Long delete = stringRedisTemplate.opsForHash().delete(tokenKey, keys.toArray());
        System.out.println(delete);*/

        Boolean delete = stringRedisTemplate.delete("login:token:b8edfbded5a74dad806b79535e44117e");
        System.out.println(delete);
    }

    @Test
    void test3(){
        Map<String, Integer> sumFollowAndFan = userMapper.getSumFollowAndFan("46540");
        for (String s : sumFollowAndFan.keySet()) {
            System.out.println(s);
            Integer i = sumFollowAndFan.get(s);
            System.out.println(i);
        }
    }

}
