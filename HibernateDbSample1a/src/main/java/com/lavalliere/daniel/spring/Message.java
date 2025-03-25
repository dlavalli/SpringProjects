package com.lavalliere.daniel.spring;

import java.io.Serializable;
import javax.persistence.*;

//  The Message.java should look familiar because that's
//  an example of a JavaBean object for the message class.

@Entity
@Table(name = "Message")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id")
    private Short id;

    @Column(name = "message")
    private String message;

    // Default constructor is required by hibernate with reflection
    // to generate instances of the class
    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    // This is part of a Java Bean definition but
    // considering the id field is auto_increment,
    // we would not generate the id ourself
    public Short getId() {
        return this.id;
    }
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