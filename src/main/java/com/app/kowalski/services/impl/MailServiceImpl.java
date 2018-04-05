package com.app.kowalski.services.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.app.kowalski.mail.Mail;
import com.app.kowalski.services.MailService;

import freemarker.template.Configuration;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration fmConfiguration;

    @Override
    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

        	System.out.println("+++ E");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(mail.getMailFrom());
            mimeMessageHelper.setTo(mail.getMailTo());
            mail.setMailContent(geContentFromTemplate(mail.getModel()));
            mimeMessageHelper.setText(mail.getMailContent(), true);

            System.out.println("+++ F");
            mailSender.send(mimeMessageHelper.getMimeMessage());

            System.out.println("+++ G");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplate(Map < String, Object > model) {
        StringBuffer content = new StringBuffer();

        try {
        	System.out.println("+++ H");
            content.append(FreeMarkerTemplateUtils
                .processTemplateIntoString(fmConfiguration.getTemplate("email-template.txt"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("+++ I");
        return content.toString();
    }

}
