package guru.springframework.sfgjms.config;

/*
 We created a new class called JmsConfig, annotated with @Configuration.
 And then, the @Bean annotation saying that I am going to have a bean called MessageConverter
 in the Spring Context. And, that it's going to be a MappingJackson2MessageConverter specifically
 for working with the Jackson JSON library. And, what Spring is going to be doing
 underneath the covers when we send a message to JMS, Spring is going to convert that message
 that we send to a JMS text message and the payload is going to be taking that Java object
 and converting that over to a JSON payload.

 This configuration is enabling our Spring instance to take JMS messages and flip those two adjacent message.
 And then, it can read that JMS message as a JMS text message and convert it back to a Java object.
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public final static String MY_QUEUE = "my-hello-world";
    public final static String MY_SEND_RCV_QUEUE = "replybacktome";

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
