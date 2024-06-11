package com.kn.initialmusic.controller;

import com.kn.initialmusic.util.JavaMailUntil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {
    public static void main(String[] args) {
        int[] ints = new int[2];
        System.out.println(ints.length);
        ints[0] = 85;
        ints[1] = 54;
        System.out.println(Arrays.toString(ints));
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("8");
        stringArrayList.add("9");
        stringArrayList.add("5");
        stringArrayList.add("5");
        stringArrayList.add("498");
        stringArrayList.add("46df");
        stringArrayList.add(5, "45");
        System.out.println(stringArrayList);
        Map<Integer, String> maptest = new HashMap<>();
        maptest.put(1, "asfd");
        maptest.put(2, "asfd");
        maptest.put(13, "asfd");
        maptest.put(189987, "asfd");
        System.out.println(maptest);


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

        System.out.println(code);
    }


}

class test1 {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;  // 月份从0开始，需要加1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        java.sql.Date date = new Date(System.currentTimeMillis());
        System.out.println(date);

        /*Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;  // 月份从0开始，需要加1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String daily = month + "/" + day;
        System.out.println(daily);*/
    }
}

class test2 {
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
