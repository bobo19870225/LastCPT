/*
 * Copyright (c) 2018. 代码著作权归卢声波所有。
 */

package www.jingkan.com.framework.email;

import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailSendUtils extends Authenticator {

    private String user;
    private String password;
    private Session session;
    private String mailHost = "smtp.gmail.com";//默认用gmail发送
    private Multipart messageMultipart;
    private Properties properties;

    static {
        Security.addProvider(new JSSEProvider());
    }

    MailSendUtils(String user, String password) {
        this.user = user;
        this.password = password;
        properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", mailHost);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.quitwait", "false");

        session = Session.getDefaultInstance(properties, this);
        messageMultipart = new MimeMultipart();
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    synchronized void sendMail(String subject, String body,
                               String sender, String recipients, String attachment) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setSender(new InternetAddress(sender));//邮件发件人
        message.setSubject(subject);//邮件主题
        //设置邮件内容
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(body);
        messageMultipart.addBodyPart(bodyPart);
//		message.setDataHandler(handler);

        //设置邮件附件
        if (attachment != null) {
            DataSource dataSource = new FileDataSource(attachment);
            DataHandler dataHandler = new DataHandler(dataSource);
            bodyPart.setDataHandler(dataHandler);
            String name = MimeUtility.encodeText(attachment.substring(attachment.lastIndexOf("/") + 1));
            bodyPart.setFileName(name);
        }
        message.setContent(messageMultipart);
        if (recipients.indexOf(',') > 0)
            //多个联系人
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients));
        else
            //单个联系人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                    recipients));
        Transport.send(message);
    }

    public String getMailHost() {
        return mailHost;
    }

    void setMailHost(String mailHost) {
        this.mailHost = mailHost;
        properties.setProperty("mail.host", this.mailHost);
    }
}
