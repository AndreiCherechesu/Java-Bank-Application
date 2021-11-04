package com.luxoft.bankapp.domain;

import java.util.List;

public class Email {
    private Client from;
    private List<Client> to;
    private String subject;
    private String body;

    public Email(Client from, List<Client> to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public Client getFrom() {
        return from;
    }

    public void setFrom(Client from) {
        this.from = from;
    }

    public List<Client> getTo() {
        return to;
    }

    public void setTo(List<Client> to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
