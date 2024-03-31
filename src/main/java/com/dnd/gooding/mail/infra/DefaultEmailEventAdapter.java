package com.dnd.gooding.mail.infra;

import com.dnd.gooding.mail.application.EmailSender;

import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Slf4j
@Component
public class DefaultEmailEventAdapter implements EmailSender {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}")
    private String fromMail;

    public DefaultEmailEventAdapter(
            JavaMailSender mailSender, SpringTemplateEngine springTemplateEngine) {
        this.mailSender = mailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    public void sendLoginSuccess(String toMail) throws Exception {
        Context context = new Context();
        String text = springTemplateEngine.process(EmailMetaData.LOGIN_SUCCESS_TEMPLATE, context);
        sendMail(EmailMetaData.LOGIN_SUCCESS_TITLE, toMail, text);
    }

    private void sendMail(String subject, String toEmail, String text) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setSubject(subject);
        helper.setTo(toEmail);
        helper.setText(text, true);
        helper.setFrom(fromMail, "gooding");

        mailSender.send(message);
    }
}
