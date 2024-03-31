package com.dnd.gooding.mail.application;

public interface EmailSender {

    void sendLoginSuccess(String toMail) throws Exception;
}
