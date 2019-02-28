package com.hp.ssm.utils;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMail {
    public static void sendMail(String to, String url,String content) {
        // 发件人电子邮箱
        String from = "1226165225@qq.com";
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1226165225@qq.com", "nwnpyxuulpsdghjd");
            }
        });
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 头部头字段
            message.setSubject("提示");


            message.setContent("<html lang='zh-CN'><head ><meta charset='utf-8'>"
                            + "</head><body>内容："+content
                            + "<a href=\"" + url + "\">确定</a></body></html>",
                    "text/html;charset=utf-8");
            // 发送消息
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
