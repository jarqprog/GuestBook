package com.jarq.controller;

import com.jarq.dao.IDaoMessage;
import com.jarq.model.Message;

import java.util.ArrayList;
import java.util.List;

public class WebMessageController implements IMessageController {

    private final IDaoMessage daoMessage;

    public static IMessageController create(IDaoMessage daoMessage) {
        return new WebMessageController(daoMessage);
    }

    private WebMessageController(IDaoMessage daoMessage) {
        this.daoMessage = daoMessage;
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messages;
        try {

             messages = daoMessage.importAll();
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
