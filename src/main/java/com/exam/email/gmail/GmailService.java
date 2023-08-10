//package com.exam.email.gmail;
//
//import com.google.api.services.gmail.Gmail;
//import com.google.api.services.gmail.model.Message;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.stereotype.Service;
//import java.io.ByteArrayOutputStream;
//import java.util.Properties;
//import javax.mail.Session;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Value;
//
//@Service
//public class GmailService {
//
//    private final Gmail gmail;
//    private final String userEmailAddress; // Your Gmail email address
//
//    public GmailService(Gmail gmail, @Value("${spring.security.oauth2.client.registration.google.client-id}") String clientId, OAuth2AuthorizedClientService clientService) {
//        this.gmail = gmail;
//        this.userEmailAddress = clientService.loadAuthorizedClient("google").getPrincipalName();
//    }
//
//    public void sendEmail(String to, String subject, String body) throws Exception {
//        MimeMessage email = createEmail(to, subject, body);
//        sendMessage(email);
//    }
//
//    private MimeMessage createEmail(String to, String subject, String bodyText) throws Exception {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session);
//        email.setFrom(new InternetAddress(userEmailAddress));
//        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
//        email.setSubject(subject);
//        email.setText(bodyText);
//
//        return email;
//    }
//
//    private void sendMessage(MimeMessage email) throws Exception {
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        email.writeTo(buffer);
//        byte[] bytes = buffer.toByteArray();
//
//        Message message = new Message();
//        message.setRaw(Base64.encodeBase64URLSafeString(bytes));
//
//        gmail.users().messages().send(userEmailAddress, message).execute();
//    }
//}
