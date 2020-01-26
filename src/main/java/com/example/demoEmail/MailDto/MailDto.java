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
    int quantity;
    long price;
    String productName;
    Date orderDate;
    String imageLink;

}
