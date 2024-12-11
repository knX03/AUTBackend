package com.kn.initialmusic;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kn.initialmusic.mapper.UserMapper;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.webSocket.pojo.FormatMess;
import com.kn.initialmusic.webSocket.pojo.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.util.RedisConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    void test3() {
/*        Map<String, Integer> sumFollowAndFan = userMapper.getSumFollowAndFan("46540");
        for (String s : sumFollowAndFan.keySet()) {
            System.out.println(s);
            Integer i = sumFollowAndFan.get(s);
            System.out.println(i);
        }*/
/*        String key = "testList";
//        stringRedisTemplate.opsForSet().add(key, "3");
        stringRedisTemplate.opsForList().remove(key, 0, "3");
        stringRedisTemplate.opsForList().leftPush(key, "3");
//        stringRedisTemplate.delete(key);
//        Set<String> strings = stringRedisTemplate.opsForSet().members(key);
        List<String> range = stringRedisTemplate.opsForList().range(key, 0, -1);
        System.out.println(range);*/
        String key0 = MESS_U1U2;
        String key = "message:ID1ID2:2227-0875";
        String key1 = "message:ID1ID2:2227-0015";
        String key2 = "message:ID1ID2:1627-2227";
        String key3 = "message:ID1ID2:2227-0415";
        String key4 = "message:ID1ID2:6297-2227";
        String key5 = "message:ID1ID2:2227-5295";
        String u1key = "2227-";
        String u2key = "0875-";
        String u3key = "2127-";
        String u4key = "2427-";
        String u5key = "2227-";
        String u6key = "2227-";
//        stringRedisTemplate.opsForList().rightPush(key, u1key + "aslsdj");
//        stringRedisTemplate.opsForList().rightPush(key, u2key + "asasldklsdj");
//        stringRedisTemplate.opsForList().rightPush(key, u1key + "aslsdj");
//        stringRedisTemplate.opsForList().rightPush(key, u2key + "asasldklsdj");
//        stringRedisTemplate.opsForList().rightPush(key, u1key + "asasldklsdj");
//        stringRedisTemplate.opsForList().rightPush(key1, u1key + "asasldklsdj");
//        stringRedisTemplate.opsForList().rightPush(key2, u1key + "asasldklsdj");
//        stringRedisTemplate.opsForList().rightPush(key3, u1key + "asasldklsdj");
//        stringRedisTemplate.opsForList().rightPush(key4, u1key + "asasldklsdj");
//        stringRedisTemplate.opsForList().rightPush(key5, u1key + "asasldklsdj");
//        Set<String> keys = stringRedisTemplate.keys(MESS_U1U2 + "*");
//        if (keys != null) {
//            for (String s : keys) {
//                int flag = s.indexOf("2227");
//                System.out.println(flag);
//                System.out.println(s.replace(MESS_U1U2, "")
//                        .replace("-", "").replace("2227", ""));
//                System.out.println(s);
//                System.out.println("------------------");
//            }
//        }
////        List<String> range = stringRedisTemplate.opsForList().range(key, 0, -1);
////        System.out.println(range);
////        if (range != null) {
////            ArrayList<mess> messes = new ArrayList<>();
////            for (String s : range) {
////                String[] strings = s.split("-");
////                mess mess = new mess();
////                mess.setUser_ID(strings[0]);
////                mess.setText(strings[1]);
////                messes.add(mess);
////            }
////            System.out.println(messes);
////        }
        List<FormatMess> formatMessesList = new ArrayList<>();
        List<FormatMess> formatMessesList1;
        List<String> range = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (range != null) {
            for (String s : range) {
                String[] strings = s.split("-");
                String userID = strings[0];
                FormatMess formatMess = new FormatMess();
                formatMess.setPoster_ID(userID);
                formatMess.setMessageText(strings[1]);
                formatMess.setPost_Time(strings[2]);
                formatMessesList.add(formatMess);
            }
        }
        formatMessesList1 = formatMessesList;
        System.out.println(formatMessesList);
        System.out.println("------------------------------");
        System.out.println(formatMessesList1);
    }

    @Test
    public void tt() {
/*        String s = "123_-_asdkh_-_2024-04-01_-_0";
        String[] strings = s.split("_-_");
        System.out.println(Arrays.toString(strings));*/
    }
}
