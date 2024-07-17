package com.kn.initialmusic.webSocket.service;

import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.mapper.MessageMapper;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.UserService;
import com.kn.initialmusic.webSocket.pojo.FormatMess;
import com.kn.initialmusic.webSocket.pojo.MessUser;
import com.kn.initialmusic.webSocket.pojo.Message;
import com.kn.initialmusic.webSocket.pojo.mess;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.controller.Code.MAIN_VALUES_NULL;
import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.util.RedisConstants.CACHE_MESS_TTL;
import static com.kn.initialmusic.util.RedisConstants.MESS_U1U2;

@Service
public class MessageService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageMapper messageMapper;

    public Result getMessageUser(String user_ID) {
        Result result = new Result();
        List<MessUser> messageUserList = messageMapper.getMessageUser(user_ID);
        //todo 使用消息队列判断缓存是否存在，若不存在则存入缓存,暂时使用方法代替
        ArrayList<String> strings = new ArrayList<>();
        for (MessUser user : messageUserList) {
            strings.add(user.getUser_ID());
        }
        saveCacheMessage(strings, user_ID);

        if (!messageUserList.isEmpty()) {
            result.setCode(SUCCESS);
            result.setData(messageUserList);
            return result;
        }
        result.setCode(MAIN_VALUES_NULL);
        return result;
    }

    public boolean saveCacheMessage(List<String> userIDList, String user_ID) {
        String key;
        String fUserID;
        String sUserID;
        for (String s : userIDList) {
            int id = Integer.parseInt(s);
            int id2 = Integer.parseInt(user_ID);
            if (id > id2) {
                key = MESS_U1U2 + s + user_ID;
                fUserID = s;
                sUserID = user_ID;
            } else {
                key = MESS_U1U2 + user_ID + s;
                fUserID = user_ID;
                sUserID = s;
            }
            List<String> range = stringRedisTemplate.opsForList().range(key, 0, -1);
            if (!range.isEmpty()) {
                stringRedisTemplate.expire(key, CACHE_MESS_TTL, TimeUnit.DAYS);
            } else {
                List<FormatMess> messagesList = messageMapper.getMessagesList(fUserID, sUserID);
                for (FormatMess formatMess : messagesList) {
                    String messText = formatMess.getPoster_ID() + "-" + formatMess.getMessageText()
                            + "-" + formatMess.getPost_Time();
                    stringRedisTemplate.opsForList().rightPush(key, messText);
                }
                stringRedisTemplate.expire(key, CACHE_MESS_TTL, TimeUnit.DAYS);
            }
        }
        return true;
    }

    public Result getMessage(String fUser_ID, String sUser_ID) {
        Result result = new Result();
        List<FormatMess> formatMessesList;
        int ID = Integer.parseInt(fUser_ID);
        int userID = Integer.parseInt(sUser_ID);
        if (ID > userID) {
            List<FormatMess> messageByCache = getMessageByCache(fUser_ID, sUser_ID);
            if (messageByCache.isEmpty()) {
                formatMessesList = messageMapper.getMessagesList(fUser_ID, sUser_ID);
            } else {
                formatMessesList = messageByCache;
            }
        } else {
            List<FormatMess> messageByCache = getMessageByCache(sUser_ID, fUser_ID);
            if (messageByCache.isEmpty()) {
                formatMessesList = messageMapper.getMessagesList(fUser_ID, fUser_ID);
            } else {
                formatMessesList = messageByCache;
            }
        }
        if (!formatMessesList.isEmpty()) {
            result.setCode(SUCCESS);
            result.setData(formatMessesList);
            return result;
        }
        result.setCode(MAIN_VALUES_NULL);
        return result;
    }

    /**
     * 从缓存中获取消息
     *
     * @param fUser_ID 第一用户ID
     * @param sUser_ID 第二用户ID
     * @return 消息对象
     */
    public List<FormatMess> getMessageByCache(String fUser_ID, String sUser_ID) {
        List<FormatMess> formatMessesList = new ArrayList<>();
        String key = MESS_U1U2 + fUser_ID + sUser_ID;
        List<String> range = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (range != null) {
            for (String s : range) {
                String[] strings = s.split("-");
                String userID = strings[0];
                FormatMess formatMess = new FormatMess();
                Result result = userService.selectDetailByID(userID);
                User poster = (User) result.getData();
                formatMess.setPoster_ID(poster.getUser_ID());
                formatMess.setPoster_Name(poster.getUser_Name());
                formatMess.setPoster_Avatar(poster.getUser_Avatar());
                formatMess.setMessageText(strings[1]);
                formatMess.setPost_Time(strings[2]);
                formatMessesList.add(formatMess);
            }
        }
        return formatMessesList;
    }

    public mess formatMessageT(Message message) {
        Result posterRE = userService.selectDetailByID(message.getPoster_ID());
        Result RecipientRE = userService.selectDetailByID(message.getRecipient_ID());
        User poster = (User) posterRE.getData();
        User Recipient = (User) RecipientRE.getData();
        mess mess = new mess();
        mess.setPoster_ID(poster.getUser_ID());
        mess.setPoster_Name(poster.getUser_Name());
        mess.setPoster_Avatar(poster.getUser_Avatar());

        mess.setRecipient_ID(Recipient.getUser_ID());
        mess.setRecipient_Name(Recipient.getUser_Name());
        mess.setRecipient_Avatar(Recipient.getUser_Avatar());

        mess.setMessageText(message.getMessage());
        mess.setPost_Time(message.getPost_time());

        return mess;
    }

    public String formatMessage(String message) {
        Message msg = JSONUtil.toBean(message, Message.class);
        String posterId = msg.getPoster_ID();
        Result posterRE = userService.selectDetailByID(posterId);
        User poster = (User) posterRE.getData();
        FormatMess formatMess = new FormatMess();
        formatMess.setPoster_ID(poster.getUser_ID());
        formatMess.setPoster_Name(poster.getUser_Name());
        formatMess.setPoster_Avatar(poster.getUser_Avatar());
        formatMess.setMessageText(msg.getMessage());
        formatMess.setPost_Time(msg.getPost_time());
        return JSONUtil.toJsonStr(formatMess);
    }

    public void saveMessage(Message message) {
        //todo 还需添加时间戳
        String key_message_reUser;
        String posterId = message.getPoster_ID();
        String recipientId = message.getRecipient_ID();
        int posterIdI = Integer.parseInt(posterId);
        int recipientIdI = Integer.parseInt(recipientId);
        if (posterIdI > recipientIdI) {
            key_message_reUser = MESS_U1U2 + posterId + recipientId;
        } else {
            key_message_reUser = MESS_U1U2 + recipientId + posterId;
        }
        String messageText = posterId + "-" + message.getMessage() + "-" + message.getPost_time();
        stringRedisTemplate.opsForList().rightPush(key_message_reUser, messageText);
    }

    //todo 暂时无法实现
    public void deleteMessage(Message message) {
        String key_message_reUser = MESS_U1U2 + message.getPoster_ID() + message.getRecipient_ID();
        stringRedisTemplate.opsForHash().delete(key_message_reUser,
                message.getPoster_ID() + "-" + message.getPost_time());

    }


    public Map<Object, Object> getUndeliveredMessages(Message message) {
        String key_message_reUser = MESS_U1U2 + message.getPoster_ID() + message.getRecipient_ID();
        return stringRedisTemplate.opsForHash().entries(key_message_reUser);
    }
}
