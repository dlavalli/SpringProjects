package guru.springframework.sfgjms;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsApplication {

	public static void main(String[] args) throws Exception {

		// Setup an embeded (for testing) server application
		// This will raise some exception but they can be ignored here for testing

		// NOTE: this configuration actually isn't necessary because if you do have
		// the server on your class path, Spring Boot is going to automatically
		// bring up a configuration for us
		ActiveMQServer server = ActiveMQServers.newActiveMQServer(
				new ConfigurationImpl()
						.setPersistenceEnabled(false)
						.setJournalDirectory("target/data/journal")
						.setSecurityEnabled(false)
						.addAcceptorConfiguration("invm", "vm://0")
		);
		server.start();

		SpringApplication.run(SfgJmsApplication.class, args);
	}

}
