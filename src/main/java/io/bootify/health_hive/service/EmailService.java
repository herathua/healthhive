package io.bootify.health_hive.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("teamnova.uom@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void sendEmailWithJsonAttachment(String to, String subject, String text, String jsonContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("teamnova.uom@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            ByteArrayResource jsonAttachment = new ByteArrayResource(jsonContent.getBytes());
            helper.addAttachment("data.json", jsonAttachment, "application/json");

            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email with JSON attachment", e);
        }
    }

    public boolean isMailServerConfigured() {
        try {
            emailSender.createMimeMessage();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}