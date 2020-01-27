package com.example.demoEmail.MailDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailDto {

    long orderNumber;
    String userEmail;
    int quantity;
    long unitPrice;
    String productName;
    Date orderDate;
    String imageLink;
    long totalPrice;
}
