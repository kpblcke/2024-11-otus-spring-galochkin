package ru.otus.hw.services;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void testSendSimpleMessage();

}
