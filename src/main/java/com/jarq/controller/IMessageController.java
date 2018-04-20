package com.jarq.controller;

import com.jarq.model.Message;

import java.util.List;

public interface IMessageController {

    List<Message> getMessages();
    boolean saveMessage(List<String> message);
}
