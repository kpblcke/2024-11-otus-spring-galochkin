package ru.otus.hw.services;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kpblcke@yandex.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void testSendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kpblcke@yandex.ru");
        message.setTo("kpblcke@gmail.com");
        message.setSubject("Afisha message");
        message.setText("I'm testing Spring mailer");
        emailSender.send(message);
    }
}