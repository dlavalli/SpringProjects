package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

// NOTE:
// when you are developing with Spring Framework, I tend to favor using this implementation.
// You can favor. You can also use JMS. What this is going to allow you to do is when
// you get further into the Spring Framework and building a larger application. This is
// abstracting the JMS portion of the code. So, if you decide to switch to a different
// messaging provider, it'll be a little less painful. So, it's kind of abstracting that out.

// import org.springframework.messaging.Message;

import javax.jms.Session;
import javax.swing.*;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    // I am injecting JmsTemplate. So, JmsTemplate is going to be a pre-configured JMS template. Much like
    // Spring has a JDBC template. Here, this is a JmsTemplate already pre-configured to
    // talk to our ActiveMQ instance. So that now is pre-configured it with all the
    // credentials for the ActiveMQ server.
    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    // Setup a schedule for executing at fixed rate in ms
    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("I am sending a message now");
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        // here, what we're doing is we're using the
        // convertAndSend method. That is going to use that message converter that we
        // provided and send it to the queue name that we set
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
        System.out.println("Message sent");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message rcvdMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");

                    System.out.println("Sending hello");

                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println(rcvdMessage.getBody(String.class));
    }
}
