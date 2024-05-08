package com.kn.initialmusic.config;

import com.kn.initialmusic.util.JavaMailUntil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

public class test {
    public static void main(String[] args) throws MessagingException {
        //	创建Session会话
        Session session = JavaMailUntil.createSession();

        /*随机生成4位验证码*/
        Random random = new Random();
        String code = "";
        // 参数length，表示生成几位随机数
        for (int i = 0; i < 4; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                code += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                code += String.valueOf(random.nextInt(10));
            }
        }
//	创建邮件对象
        MimeMessage message = new MimeMessage(session);
        message.setSubject("AUT音乐");
        message.setText("您的注册验证码为：" + code);
        message.setFrom(new InternetAddress("2254990841@qq.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2254990841@qq.com"));

//	发送
        Transport.send(message);
    }

}
