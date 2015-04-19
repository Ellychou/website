package com.bostonangelclub.kit;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by szhou on 2015/3/10.
 */
public class SendMailKit {

        public static void send(String toEmail, String content) {
            final String username = Const.MAILGUN_USERNAME;
            final String password = Const.MAILGUN_PASSWORD;
            final String fromEmail = "SetIndustryNotify@bostonangelclub.com";
            final String fromName = "Boston Angel Club";
            final String subject = "New project has been saved to database, please set industry";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.mailgun.org");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                try {
                    message.setFrom(new InternetAddress(fromEmail, fromName));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject(subject);
                message.setContent(content,"text/html");

                Transport.send(message);

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }


}
