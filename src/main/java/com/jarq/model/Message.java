package com.jarq.model;

import java.time.LocalDateTime;

public class Message {

    private int id;
    private String authorName;
    private String content;
    private LocalDateTime date;


    public Message(int id, String authorName, String content, LocalDateTime date) {
        this(authorName, content);
        this.id = id;
        this.date = date;
    }

    public Message(String authorName, String content) {
        this.authorName = authorName;
        this.content = content;
        this.date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
