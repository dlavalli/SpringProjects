package com.lavalliere.daniel.spring.rabbitmq.messaging;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

/*
    The Receiver is a POJO that defines a method for receiving messages.
    When you register it to receive messages, you can name it anything you want.
    Using  new MessageListenerAdapter(receiver, "receiveMessage");  to wrap the POJO to manage incomming messages
 */

@Component
public class Receiver {

    // For convenience, this POJO also has a CountDownLatch.
    // This lets it signal that the message has been received.
    // This is something you are not likely to implement in a production application.
    private CountDownLatch latch = new CountDownLatch(1);

    // SEE:  https://docs.spring.io/spring-amqp/docs/current/api/org/springframework/amqp/rabbit/listener/adapter/MessageListenerAdapter.html

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    // DO NOT DO THAT IN PRODUCTION
    public CountDownLatch getLatch() {
        return latch;
    }

    /*
         Example for using header information in the messages
         1-) You must put Headers in your Message:

             MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
             props.setHeader("headerKey1", "headerValue1");
             Message msg = new Message("{'body':'value1','body2':value2}".getBytes(), props);
             rabbitTemplate.send("exchange.direct.one", new String(), msg);

         2-) For read the headers of Message inbound from Rabbit Queue:

             import org.springframework.amqp.core.Message;
             import org.springframework.amqp.core.MessageListener;

             public class MessagesHandler implements MessageListener {
                 public void onMessage(Message message) {
                     Map<String, Object> headers = message.getMessageProperties().getHeaders();
                     for (Map.Entry<String, Object> header : headers.entrySet())
                     {
                         System.out.println(header.getKey() + " : " + header.getValue());
                     }
                 }
             }
     */

}
