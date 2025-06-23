package com.leavemanagement.LeaveManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        System.out.println(">>> Sending email to: " + to);
        System.out.println(">>> Subject: " + subject);
        System.out.println(">>> Body: " + body);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gudurusuchi2003@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println(">>> Email sent to " + to);
    }
}
