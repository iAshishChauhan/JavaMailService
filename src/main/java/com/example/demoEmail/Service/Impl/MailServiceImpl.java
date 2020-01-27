package com.example.demoEmail.Service.Impl;

import com.example.demoEmail.MailDto.MailDto;
import com.example.demoEmail.Service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
// CommandRunner
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = "MailTopic", groupId = "group-id", containerFactory = "kafkaListenerContainerFactory")
    public void sendEmail(String mailDTOString) throws MessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MailDto mailDto = new MailDto();
        try {
            mailDto = objectMapper.readValue(mailDTOString,MailDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("team7coviam@gmail.com", "test123test");
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("team7coviam@gmail.com", false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ashish.singh@coviam.com"));

        List<MailDto> list = new ArrayList<>();

        MailDto mailDto1 = new MailDto();

        mailDto1.setOrderNumber(mailDto.getOrderNumber());
        mailDto1.setImageLink(mailDto.getImageLink());
        mailDto1.setOrderDate(mailDto.getOrderDate());
        mailDto1.setUnitPrice(mailDto.getUnitPrice());
        mailDto1.setTotalPrice(mailDto.getTotalPrice());
        mailDto1.setQuantity(mailDto.getQuantity());
        mailDto1.setUserEmail(mailDto.getUserEmail());
        mailDto1.setProductName(mailDto.getProductName());

        list.add(mailDto1);
        long totalPriceOfOrder = 0;

        String mailContent = "<div style='border-style:double'><h1 style='color:green; font-family:fantasy; text-align:center;'>AmmuNation</h1><br>";

        for(MailDto m:list) {
            mailContent=mailContent+"<div style='border-style:double'><img src='"+m.getImageLink()+"' style='max-height:300px;'><div style='border-style:dotted'><table><tr><td>Name</td><td> : </td><td>"+m.getProductName()+"</td></tr><tr>"+
                    "<tr><td>QUANTITY</td><td>:</td><td>"+m.getQuantity()+"</td></tr><tr><td>UNIT PRICE</td><td>:</td><td>"
                    +m.getUnitPrice()+"</td></tr><tr><td>TOTAL PRICE</td><td>:</td><td>"+m.getTotalPrice()+"</td></tr><table></div></div><br>";

            totalPriceOfOrder=totalPriceOfOrder+m.getTotalPrice();
        }

        mailContent=mailContent+"TOTAL AMOUNT:"+totalPriceOfOrder+"/-</div>";

        msg.setSubject("********* ammuNation ORDER *********");

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(mailContent, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
        System.out.println("email sent successfully !");

    }
}
