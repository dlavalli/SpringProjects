package com.lavalliere.daniel.spring;

import java.io.Serializable;

//  The Message.java should look familiar because that's
//  an example of a JavaBean object for the message class.
public class Message implements Serializable {
    private Short id;
    private String message;

    // Default constructor is required by hibernate with reflection
    // to generate instances of the class
    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public Short getId() {
        return this.id;
    }

    // This is part of a Java Bean definition but
    // considering the id field is auto_increment,
    // we would not generate the id ourself
    public void setId(Short id) {
        this.id = id;
    }
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
