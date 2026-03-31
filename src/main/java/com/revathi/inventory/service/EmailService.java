package com.revathi.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendLowStockEmail(String[] recipients, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("revathichandratannidi@gmail.com");
        message.setTo(recipients);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
