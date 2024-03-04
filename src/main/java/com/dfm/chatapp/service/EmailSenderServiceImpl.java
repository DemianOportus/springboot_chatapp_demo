package com.dfm.chatapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl {
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage htmlmessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(htmlmessage, true, "UTF-8");
        helper.setFrom("donotreply.chatapp@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);

        String htmlBody = "<html>" +
                "<body>" +
                "<div style='border-bottom:1px solid black; padding:10px; text-align: center;'>" +
                "<img src='cid:companyLogo' style='width:100%;'/>" +  // Adjust width and height as needed
                "</div>" +
                "<p style='white-space: pre-wrap;'>" + body + "</p>" +  // This will preserve line breaks in your text
                "</body>" +
                "</html>";
        helper.setText(htmlBody, true);

        ClassPathResource image = new ClassPathResource("static/images/header.png");
        helper.addInline("companyLogo", image, "header/png");

        mailSender.send(htmlmessage);

        System.out.println("Mail sent successfully...");
    }
}