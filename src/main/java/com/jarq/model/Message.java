package com.jarq.model;

import java.time.LocalDate;

public class Message {

    private int id;
    private String authorName;
    private String content;
    private LocalDate date;


    public Message(int id, String authorName, String content, LocalDate date) {
        this(authorName, content, date);
        this.id = id;
    }

    public Message(String authorName, String content, LocalDate date) {
        this.authorName = authorName;
        this.content = content;
        this.date = date;
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

    public LocalDate getDate() {
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
