package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MessageService {

    /**
     * 发送消息至用户
     *
     * @param message 消息对象
     * @param user_ID 用户ID
     * @return 是否发送成功
     */

    Boolean postMessage(Message message, String user_ID);

    /**
     * 获取用户的消息
     *
     * @param user_ID 用户ID
     * @return 消息列表
     */
    List<Message> getMessages(String user_ID);

    /**
     * 删除指定用户的指定消息
     *
     * @param user_ID 用户ID
     * @param mess_ID 消息ID
     * @return 是否删除成功
     */
    Boolean delMess(String user_ID, String mess_ID);
}
