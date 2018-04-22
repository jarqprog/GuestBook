package com.jarq.controller;

import com.jarq.dao.IDaoMessage;
import com.jarq.model.Message;

import java.util.*;

public class WebMessageController implements IMessageController {

    private final IDaoMessage daoMessage;

    public static IMessageController create(IDaoMessage daoMessage) {
        return new WebMessageController(daoMessage);
    }

    private WebMessageController(IDaoMessage daoMessage) {
        this.daoMessage = daoMessage;
    }

    @Override
    public List<String[]> getMessages() {

        List<String[]> messages = new ArrayList<>();
        String[] message;

        int messageLen = 3;

        int messageIndex = 0;
        int guestIndex = 1;
        int dateIndex = 2;


        try {
            List<Message> importedMessages = daoMessage.importAll();
            for(Message msg : importedMessages) {

                message = new String[messageLen];

                message[messageIndex] = "<span style=\"font-style: italic;\">" + msg.getContent() + "</span>";
                message[guestIndex] = "<span style=\"font-weight: bold;\"> author: " + msg.getAuthorName() + "</span>";
                message[dateIndex] = "date: " + String.valueOf(msg.getDate());

                messages.add(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return messages;
    }

    @Override
    public boolean saveMessage(List<String> message) {
        return false;
    }
}
