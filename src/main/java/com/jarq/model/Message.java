package com.jarq.model;

public class Message {

    private int id;
    private String authorName;
    private String content;


    public Message(int id, String authorName, String content) {
        this(authorName, content);
        this.id = id;
    }

    public Message(String authorName, String content) {
        this.authorName = authorName;
        this.content = content;
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
