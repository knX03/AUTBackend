package com.kn.initialmusic.service;

import com.kn.initialmusic.util.JavaMailUntil;
import jakarta.annotation.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Service
public class EmailSendService {
    @Resource
    private JavaMailSender javaMailSender;

    public Boolean sendCode(String userEmail, String code) throws MessagingException {
        //	创建Session会话
        Session session = JavaMailUntil.createSession();
        System.out.println(userEmail + ":" + code);
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        message.setSubject("AUT音乐");
        message.setText("您的验证码为：" + code + "\n有效期5分钟。");
        message.setFrom(new InternetAddress("x15007623521@163.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        System.out.println(message);
        //发送
        Transport.send(message);

        return true;
    }

    public Boolean sendCode2(String userEmail, String code) throws MessagingException, jakarta.mail.MessagingException, UnsupportedEncodingException {
        // 创建一个邮件消息
        jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();

        // 创建 MimeMessageHelper
        MimeMessageHelper helper = new MimeMessageHelper(message, false);

        // 发件人邮箱和名称
        helper.setFrom("x15007623521@qq.com", "springdoc");
        // 收件人邮箱
        helper.setTo(userEmail);
        // 邮件标题
        helper.setSubject("Hello");
        // 邮件正文，第二个参数表示是否是HTML正文
        helper.setText("<strong> code</strong>！", true);

        // 发送
        javaMailSender.send(message);
        return true;
    }


}
