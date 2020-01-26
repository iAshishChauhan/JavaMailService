package com.example.demoEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

@SpringBootApplication
public class DemoEmailApplication implements CommandLineRunner {

    @Autowired
	private JavaMailSender javaMailSender;

	public static void main(String[] args) {
		SpringApplication.run(DemoEmailApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {

        System.out.println("Sending Email...");

        try {
            sendEmail();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Done");

    }
    void sendEmail() throws MessagingException {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("ashish.singh@coviam.com");

        msg.setSubject("Order Confirmation");
        MimeBodyPart messageBody = new MimeBodyPart();

        String mailContent = "<div style='border-style:double'><h1 style='color:green; font-family:fantasy; text-align:center;'>Ammu Nation</h1><br>";

        messageBody.setContent(mailContent, "text/html");

        msg.setText("");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBody);


        javaMailSender.send(msg);
    }




}
