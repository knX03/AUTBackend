package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.MessageMapper;
import com.kn.initialmusic.pojo.Message;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private GenerateIDService generateIDService;

    @Override
    public Boolean postMessage(Message message, String user_ID) {
        String messageID = generateIDService.generateMessageID();
        message.setMessage_id(messageID);
        int meFlag = messageMapper.createMessage(message);
        if (meFlag > 0) {
            int flag = messageMapper.insertUserMessage(messageID, user_ID);
            return flag > 0;
        }
        return false;
    }

    @Override
    public List<Message> getMessages(String user_ID) {
        List<Message> messages = messageMapper.getMessages(user_ID);
        return messages;
    }

    @Override
    public Boolean delMess(String user_ID, String mess_ID) {
        int i = messageMapper.delMess(user_ID, mess_ID);
        return i > 0;
    }
}
