package com.example.nvclothes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        String admin = System.getenv("ADMIN");
        message.setFrom(System.getenv("ADMIN"));
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        //mailSender.send(message);
        log.info("Mail Send...");
    }

}
