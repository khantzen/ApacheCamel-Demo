package com.kevin.demo.camel.common.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by khantzen on 14/08/17.
 */
@Entity
@Table(name="Audit")
public class LogMessage implements Serializable{
    @Id
    @Column(name="id")
    private int id;
    @Column(name = "Message")
    private String message;
    @Column(name = "Sender")
    private String sender;
    @Column(name="Owner")
    private String owner;
    @Column(name="Level")
    private String logLevel;

    public LogMessage() {}

    public LogMessage(Builder builder) {
        this.message = builder.message;
        this.sender = builder.sender;
        this.owner = builder.owner;
        this.logLevel = builder.logLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public static class Builder {
        private String message;
        private String sender;
        private String owner;
        private String logLevel;


        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public Builder setOwner(String owner) {
            this.owner = owner;
            return this;
        }


        public Builder setLogLevel(String logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public LogMessage build() {
            return new LogMessage(this);
        }
    }
}
