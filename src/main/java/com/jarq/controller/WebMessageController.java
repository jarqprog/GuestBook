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
                String[] rawDateTime = String.valueOf(msg.getDate()).split("T");
                int dateIdx = 0;
                int timeIdx = 1;
                int numberNanosecondsToRemove = 4;  // removes unnecessary nanoseconds from time
                String date = rawDateTime[dateIdx];
                String time = rawDateTime[timeIdx].substring(0, rawDateTime[timeIdx].length()-numberNanosecondsToRemove);
                message[dateIndex] = "date: " + date + " " + time;
                messages.add(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return messages;
    }

    @Override
    public boolean saveMessage(String messageContent, String guestName) {
        try {
            return daoMessage.write(new Message(guestName, messageContent));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
