package com.javarush.task.task40.task4003;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/*
Отправка письма с файлом
*/

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.sendMail("music.xbe@gmail.com", "severen2001", "promverstat@yahoo.com");
    }

    public void sendMail(final String username, final String password, final String recipients) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.transport.protocol", "smtps");


        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        try {
            Message message = new MimeMessage(session);
//            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

            setSubject(message, "Тестовое письмо");
            setAttachment(message, "/Users/Documents/text.txt");


            Transport.send(message);
            System.out.println("Письмо было отправлено.");

        } catch (MessagingException e) {
            System.out.println("Ошибка при отправке: " + e.toString());
        }
    }

    public static void setSubject(Message message, String subject) throws MessagingException {
        message.setSubject(subject);
    }

    public static void setAttachment(Message message, String filename) throws MessagingException {
//        Multipart multipart = new MimeMultipart();
//        MimeBodyPart messageBodyPart = new MimeBodyPart();
//
//        messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setFileName(filename);
//        multipart.addBodyPart(messageBodyPart);



        Multipart multipart = new MimeMultipart();
        MimeBodyPart textBodyPart = new MimeBodyPart();
//        textBodyPart.setText("your text");

        MimeBodyPart attachmentBodyPart= new MimeBodyPart();
        DataSource source = new FileDataSource(filename);       // ex : "C:\\test.pdf"
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(filename);                       // ex : "test.pdf"

//        multipart.addBodyPart(textBodyPart);  // add the text part
        multipart.addBodyPart(attachmentBodyPart); // add the attachement part

        message.setContent(multipart);


//        message.setText(filename);
    }
}

/*
Properties props = new java.util.Properties();
    props.put("mail.smtp.host", "yourHost");
    props.put("mail.smtp.port", "yourHostPort");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");


    // Session session = Session.getDefaultInstance(props, null);
    Session session = Session.getInstance(props,
              new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("user", "password");
                }
              });


    Message msg = new MimeMessage(session);
    try {
        msg.setFrom(new InternetAddress(mailFrom));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        msg.setSubject("your subject");

        Multipart multipart = new MimeMultipart();

        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("your text");

        MimeBodyPart attachmentBodyPart= new MimeBodyPart();
        DataSource source = new FileDataSource(attachementPath); // ex : "C:\\test.pdf"
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        attachmentBodyPart.setFileName(fileName); // ex : "test.pdf"

        multipart.addBodyPart(textBodyPart);  // add the text part
        multipart.addBodyPart(attachmentBodyPart); // add the attachement part

        msg.setContent(multipart);


        Transport.send(msg);
    } catch (MessagingException e) {
        LOGGER.log(Level.SEVERE,"Error while sending email",e);
    }


 */

/*
Отправка письма с файлом
Исправь реализацию метода setAttachment. Этот метод должен прикреплять файл к письму.
Подсказки:
1. Используй библиотеку JavaMail API версии 1.4.7.
2. Письмо должно содержать только одну часть (MimeBodyPart) с файлом.
Требования:
1. Метод setAttachment должен устанавливать новый контент у сообщения message. Тип контента должен быть MimeMultipart.
2. После вызова метода setAttachment, контент сообщения message должен содержать одну часть MimeBodyPart.
3. Метод setAttachment должен корректно устанавливать файл в соответствующий MimeBodyPart объект.
4. Метод setAttachment должен корректно устанавливать имя файла в соответствующий MimeBodyPart объект.
 */