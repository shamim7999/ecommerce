package org.dsi.ecommerce.services;

import jakarta.mail.SendFailedException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage message) {
        try {
            javaMailSender.send(message);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception MAIL SENDING");
        }
    }
}
