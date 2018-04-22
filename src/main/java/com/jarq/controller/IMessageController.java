package com.jarq.controller;

import java.util.List;

public interface IMessageController {

    List<String[]> getMessages();
    boolean saveMessage(String messageContent, String guestName);
}
