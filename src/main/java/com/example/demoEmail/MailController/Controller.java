package com.example.demoEmail.MailController;

import com.example.demoEmail.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class Controller {
    @Autowired
    MailService mailService;

    @GetMapping("/send")
    void sendConfirmationMail() throws MessagingException {
        mailService.sendEmail();
    }


}
