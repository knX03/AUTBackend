package com.kn.initialmusic.service;

import com.kn.initialmusic.util.JavaMailUntil;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSendService {

    public Boolean sendCode(String userEmail, String code) throws MessagingException {
        //	创建Session会话
        Session session = JavaMailUntil.createSession();

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        message.setSubject("AUT音乐");
        message.setText("您的验证码为：" + code + "\n有效期5分钟。");
        message.setFrom(new InternetAddress("2254990841@qq.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

        //发送
        Transport.send(message);

        return true;
    }
}
