package io.bootify.health_hive.rest;


import io.bootify.health_hive.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    static class EmailRequest {
        private String to;
        private String subject;
        private String text;

        // Getters and setters
        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }



    @GetMapping("/check")
    public String checkEmailService() {
        boolean isConfigured = emailService.isMailServerConfigured();
        if (isConfigured) {
            return "Mail server is configured correctly!";
        } else {
            return "Mail server configuration is incorrect!";
        }
    }
}