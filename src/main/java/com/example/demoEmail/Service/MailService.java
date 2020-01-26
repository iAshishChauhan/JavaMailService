package com.example.demoEmail.Service;

import javax.mail.MessagingException;

public interface MailService {
    void sendEmail() throws MessagingException;
}
