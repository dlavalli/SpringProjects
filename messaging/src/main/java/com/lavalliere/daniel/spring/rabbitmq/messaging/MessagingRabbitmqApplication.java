package com.lavalliere.daniel.spring.rabbitmq.messaging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagingRabbitmqApplication {

	/*
		Origin:
		https://spring.io/guides/gs/messaging-rabbitmq/#initial

	 	JMS queues and AMQP queues have different semantics.
	 	For example, JMS sends queued messages to only one consumer.
	 	While AMQP queues do the same thing, AMQP producers do not send messages
	 	directly to queues. Instead, a message is sent to an exchange, which can
	 	go to a single queue or fan out to multiple queues,
	 	emulating the concept of JMS topics.

	 	The message listener container and receiver beans are all you need to listen for messages.
	 	To send a message, you also need a Rabbit template.

		If you use Maven, you can run (from intellij terminal) the application by using
			./mvnw spring-boot:run
		Alternatively, you can build the JAR file with
			./mvnw clean package
		and then run the JAR file, as follows:
			java -jar target/gs-messaging-rabbitmq-0.1.0.jar


		NOTE:	Since by default @SpringBootAppication also maps @Configuration all the necessary beans are created and managed
			  	BUT  in a real application, this would be managed in another file using @Configuration annotation
			  	ex:
			  		@Configuration
 					public class MyAutoConfiguration {
						@ConditionalOnMissingBean
     					@Bean
     					public MyService myService() {
         				...
     					}
					}
	 */

	static final String topicExchangeName = "spring-boot-exchange";
	static final String queueName = "spring-boot";

	@Bean
	Queue queue() {
		// The queue() method creates a private AMQP queue
		// which disapear when closing the application
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		// The exchange() method creates a topic exchange
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		// The binding() method binds the new queue and and exchange together,
		// defining the behavior that occurs when RabbitTemplate publishes to an exchange.

		// IMPORTANT:
		// Spring AMQP requires that the Queue, the TopicExchange, and the Binding
		// be declared as top-level Spring beans in order to be set up properly.

		// In this case, we use a topic exchange, and the queue is bound with
		// a routing key of foo.bar.#, which means that any messages sent with
		// a routing key that begins with foo.bar. are routed to the queue.
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		// The bean defined in the listenerAdapter() method is registered as
		// a message listener in the container (defined in container()).
		// It listens for messages on the spring-boot queue.
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		// Because the Receiver class
		// is a POJO, it needs to be wrapped in the MessageListenerAdapter,
		// where you specify that it invokes receiveMessage.
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MessagingRabbitmqApplication.class, args).close();
	}

}
