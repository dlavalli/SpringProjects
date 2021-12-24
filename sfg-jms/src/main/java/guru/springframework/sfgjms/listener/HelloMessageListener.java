package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

// NOTE:
// when you are developing with Spring Framework, I tend to favor using this implementation.
// You can favor. You can also use JMS. What this is going to allow you to do is when
// you get further into the Spring Framework and building a larger application. This is
// abstracting the JMS portion of the code. So, if you decide to switch to a different
// messaging provider, it'll be a little less painful. So, it's kind of abstracting that out.

// import org.springframework.messaging.Message;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(
            // this @Payload annotation is going to tell Spring Framework to go ahead and deserialize up component.
            // The payload of that JMS message, it says, I'm expecting you to get a message,
            // a HelloWorldMessage from this queue.
            @Payload HelloWorldMessage helloWorldMessage,

            // The following fields and optional, depending on your needs

            // This headers annotation is going to tell Spring Framework to go ahead
            // and get the MessageHeaders. And in this case, it's going to be equivalent
            // to the JMS message properties and the header properties.
            @Headers MessageHeaders headers,
            Message message) {
       // System.out.println("Just received a message");
       // System.out.println(helloWorldMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(
            // this @Payload annotation is going to tell Spring Framework to go ahead and deserialize up component.
            // The payload of that JMS message, it says, I'm expecting you to get a message,
            // a HelloWorldMessage from this queue.
            @Payload HelloWorldMessage helloWorldMessage,

            // The following fields and optional, depending on your needs

            // This headers annotation is going to tell Spring Framework to go ahead
            // and get the MessageHeaders. And in this case, it's going to be equivalent
            // to the JMS message properties and the header properties.
            @Headers MessageHeaders headers,
            Message message) throws JMSException {

        HelloWorldMessage replyMessage = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), replyMessage);
    }
}
