package com.jarq.dao;

import com.jarq.model.Message;

import java.util.List;

public interface IDaoMessage {

    // throws Exception instead of SQLException because interface is not only for SQL-like database
    boolean write(Message message) throws Exception;
    List<Message> importAll() throws Exception;
}
